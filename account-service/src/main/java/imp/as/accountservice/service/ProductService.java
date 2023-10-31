package imp.as.accountservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imp.as.accountservice.exception.BusinessException;
import imp.as.accountservice.model.Product;
import imp.as.accountservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
	//Repository
	@Autowired
	private final ProductRepository productRepository;
	
	public Product getProductByProductId(Integer productId) throws BusinessException {
		Optional<Product> optional = productRepository.findById(productId);
		
		if (optional.isEmpty()) {
            throw new BusinessException("Data not found");
        }
		
		return optional.get();
	}
}
