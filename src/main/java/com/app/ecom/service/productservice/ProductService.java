package com.app.ecom.service.productservice;

import com.app.ecom.dto.productdto.ProductRequestDTO;
import com.app.ecom.dto.productdto.ProductResponseDTO;
import com.app.ecom.mapper.ProductMapper;
import com.app.ecom.model.product.Product;
import com.app.ecom.repository.productrepository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductResponseDTO> getAllProducts() {
        // Fetch products from the repository and convert to DTOs
    List<Product> products = productRepository.findAll();
        // Convert to ResponseDTOs
        return products.stream()
                .map(productMapper::toProductResponse).toList();

    }

    public ProductResponseDTO getProductById(Long id) {
        // Fetch product by ID and convert to DTO
        return productRepository.findById(id)
                .map(productMapper::toProductResponse)
                .orElse(null);
    }

    public ProductResponseDTO createProduct(ProductRequestDTO productRequest) {
        // Convert DTO to Product model and save to repository
        Product product = productMapper.toProductModel(productRequest);
        Product savedProduct = productRepository.save(product);
        return productMapper.toProductResponse(savedProduct);
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productUpdateRequest) {
        // Fetch existing product, update fields, and save
        return productRepository.findById(id)
                .map(existingProduct -> {
                    Product updatedProduct = productMapper.toUpdateProductFromRequestDTO(existingProduct, productUpdateRequest);
                    Product savedProduct = productRepository.save(updatedProduct);
                    return productMapper.toProductResponse(savedProduct);
                })
                .orElse(null);
    }

    public boolean deleteById(Long id) {
        // Delete product by ID
        boolean exists = productRepository.existsById(id);
        if (exists) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
