package imp.as.paymentservice.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imp.as.paymentservice.constant.AppConstant;
import imp.as.paymentservice.dto.request.AccountBalanceRequest;
import imp.as.paymentservice.dto.request.TransactionRequest;
import imp.as.paymentservice.dto.response.InvoiceResponse;
import imp.as.paymentservice.exception.BusinessException;
import imp.as.paymentservice.model.InvoiceBalance;
import imp.as.paymentservice.repository.InvoiceBalanceRepository;
import imp.as.paymentservice.utils.DateTimeUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class InvoiceService {
	@Autowired
	private InvoiceBalanceRepository invoiceRepository;
	
	@Autowired
	private AccountBalanceService accountBalanceService;
	
	private String generateInvoiceNum(String curInv, Integer year, Integer month) {
		int currentInvoice = 0;
		
		if(curInv == null) {
			curInv = invoiceRepository.getInvoiceCurrentSeq();
		}
		
		if(curInv != null) {
			currentInvoice = Integer.parseInt(curInv.substring(8));
		}
		
		Calendar calendar = Calendar.getInstance(DateTimeUtils.APP_DEFAULT_LOCALE);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		
		String dateFor = DateTimeUtils.formatDate(AppConstant.INVOICE_NUM_FORMAT, calendar.getTime());
		
		currentInvoice += 1;
		
		return AppConstant.INVOICE_TYPE_IN + "-" + dateFor + "-" + String.format("%04d", currentInvoice);
	}
	
	public InvoiceBalance generateInvoice(AccountBalanceRequest request
											, String userName, Integer year, Integer month) {
		String currentSeq = generateInvoiceNum(null, year, month);
		
		Calendar calendar = Calendar.getInstance(DateTimeUtils.APP_DEFAULT_LOCALE);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		InvoiceBalance invoice = new InvoiceBalance();
		invoice.setInvoiceNum(currentSeq);
		invoice.setAccountNo(request.getAccountNo());
		invoice.setInvoiceType(AppConstant.INVOICE_TYPE_IN);
		invoice.setInvoiceDate(calendar.getTime());
		invoice.setBillSeq(1);
		
		calendar.add(Calendar.DATE, 7);
		invoice.setDueDate(calendar.getTime());
		
		invoice.setInvoiceMny(request.getTotalBalance());
		invoice.setTotalBalance(request.getTotalBalance());
		invoice.setCreated(new Date());
		invoice.setCreatedBy(userName);
		invoice.setLastUpd(new Date());
		invoice.setLastUpdBy(userName);
		
		invoiceRepository.save(invoice);
		
		return invoice;
	}
	
	public List<InvoiceResponse> generateInvoices(List<AccountBalanceRequest> accountBalanceRequests
							, String userName, Integer year, Integer month) throws BusinessException{
		try {
			for(AccountBalanceRequest request : accountBalanceRequests) {
				InvoiceBalance invoice = generateInvoice(request, userName, year, month);
				
				TransactionRequest transac = new TransactionRequest();
				transac.setAccountNo(request.getAccountNo());
				transac.setInvoiceNum(invoice.getInvoiceNum());
				transac.setMny(request.getTotalBalance());
				transac.setMovementType(AppConstant.MOVEMENT_TYPE_IN);
				transac.setUserName(userName);
				transac.setSeqNum(1);
				
				accountBalanceService.updateAccountBalance(transac);
			}
			
			return null;
		}catch (Exception e) {
			throw new BusinessException(e);
		}
	}
}

