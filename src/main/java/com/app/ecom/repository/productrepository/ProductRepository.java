package com.app.ecom.repository.productrepository;

import com.app.ecom.dto.productdto.ProductResponseDTO;
import com.app.ecom.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByIsActiveTrue();
    List<Product> findByNameContainingIgnoreCaseAndIsActiveTrue(String keyword);
}
