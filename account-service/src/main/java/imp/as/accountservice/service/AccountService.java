package imp.as.accountservice.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import imp.as.accountservice.constant.AppConstant;
import imp.as.accountservice.constant.EndpointConstant;
import imp.as.accountservice.dto.request.AccountBalanceRequest;
import imp.as.accountservice.dto.request.AccountRequest;
import imp.as.accountservice.dto.response.AccountResponse;
import imp.as.accountservice.exception.BusinessException;
import imp.as.accountservice.kafka.ProducerService;
import imp.as.accountservice.model.Account;
import imp.as.accountservice.model.AssetPromotion;
import imp.as.accountservice.model.Mobile;
import imp.as.accountservice.model.Product;
import imp.as.accountservice.repository.AccountRepository;
import imp.as.accountservice.utils.DateTimeUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService{
	@Autowired
	private final WebClient.Builder webClientBuilder;
	
	@Autowired
	private final AccountRepository accountRepository;
	
	@Autowired
	private final ProducerService producerService;
	
	@Autowired
	private final EntityManager entityManager;
	
	public String getNextAccountNo() {
		String dateFor = DateTimeUtils.formatDate(AppConstant.ACCOUNT_NO_FORMAT, DateTimeUtils.currentDate());
		
		Optional<String> preassignOpt = accountRepository.getNextValAccountNoSequence();
		
		if(!preassignOpt.isEmpty()) {
			Integer nextId = Integer.parseInt(preassignOpt.get().substring(preassignOpt.get().length() - 4)) + 1;
			
			return dateFor + String.format("%04d", nextId);
		}else {
			return dateFor + "0001";
		}
	}
	
	private Account generateAccount(AccountRequest accountRequest) {
		Account account = new Account();
		account.setAccountNo(getNextAccountNo());
		account.setAccountName(accountRequest.getAccountName());
		account.setStatus(AppConstant.ACC_STATUS_ACTIVE);
		account.setStatusDate(new Date());
		account.setCreated(new Date());
		account.setCreatedBy(accountRequest.getUserName());
		account.setLastUpd(new Date());
		account.setLastUpdBy(accountRequest.getUserName());
		
		return account;
	}
	
	public AccountResponse createAccount(AccountRequest accountRequest) throws BusinessException {
		try {
			Account account = generateAccount(accountRequest);
			
			accountRepository.save(account);
			
			producerService.sendMessageAccountTopic(account.getCreateAccountTopicRequest());
			
			return AccountResponse.builder()
						.accountNo(account.getAccountNo())
						.accountId(account.getAccountId())
						.build();
		}catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	public List<AccountResponse> getAccount() {
		List<Account> accounts = accountRepository.findAll();
		
		return accounts.stream().map(Account::getAccountResponse).toList();
	}
	
	public Account getAccountByAccountNo(String accountNo) throws BusinessException {
		Optional<Account> optional = accountRepository.findByAccountNo(accountNo);
		
		if (optional.isEmpty()) {
            throw new BusinessException("Data not found");
        }
		
		return optional.get();
	}
	
	public void generateInvoice(Integer year, Integer month, String userName) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Account> criteriaQuery = cb.createQuery(Account.class);
		Root<Account> root = criteriaQuery.from(Account.class);
		Join<Account, Mobile> parentJoin = root.join("mobiles", JoinType.INNER);
		Join<Mobile, AssetPromotion> assetJoin = parentJoin.join("assetPromotions", JoinType.INNER);
		Join<AssetPromotion, Product> productJoin = assetJoin.join("product", JoinType.INNER);
		
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.equal(parentJoin.get("status"), "Active"));
		predicates.add(cb.equal(assetJoin.get("status"), "Active"));
		predicates.add(cb.equal(productJoin.get("status"), "Active"));
		
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
        List<Account> accounts = entityManager.createQuery(criteriaQuery).getResultList();
        
        List<AccountBalanceRequest> accountBalanceRequests = new ArrayList<>();
        
        for(Account account : accounts) {
        	BigDecimal proPrice = BigDecimal.ZERO;
        	
			for(Mobile mobile : account.getMobiles()) {
				for(AssetPromotion assetPromotion : mobile.getAssetPromotions()) {
					proPrice = proPrice.add(assetPromotion.getProduct().getPrice());
				}
			}
			
			accountBalanceRequests.add(AccountBalanceRequest.builder()
							.accountNo(account.getAccountNo())
							.totalBalance(proPrice)
							.build());
		}
        
        if(!accountBalanceRequests.isEmpty()) {
        	webClientBuilder.build().post()
        				.uri(EndpointConstant.ENDPOINT_PAYMENT + EndpointConstant.METHOD_GEN_INVOICES,
        						uriBuilder -> uriBuilder.queryParam("year", year)
        										.queryParam("month", month)
        										.queryParam("userName", userName).build())
        				.body(Mono.just(accountBalanceRequests), AccountBalanceRequest.class)
        				.retrieve()
        				.toEntity(String.class)
        				.block();
        }
	}
}
