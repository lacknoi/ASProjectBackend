package imp.as.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imp.as.accountservice.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
