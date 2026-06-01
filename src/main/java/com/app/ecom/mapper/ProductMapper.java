package com.app.ecom.mapper;

import com.app.ecom.dto.productdto.ProductRequestDTO;
import com.app.ecom.dto.productdto.ProductResponseDTO;
import com.app.ecom.model.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toUpdateProductFromRequestDTO(Product product, ProductRequestDTO productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        return product;
    }

    public ProductResponseDTO toProductResponse(Product product) {
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(product.getId());
        responseDTO.setName(product.getName());
        responseDTO.setDescription(product.getDescription());
        responseDTO.setPrice(product.getPrice());
        responseDTO.setStockQuantity(product.getStockQuantity());
        responseDTO.setCategory(product.getCategory());
        responseDTO.setImageUrl(product.getImageUrl());
        responseDTO.setIsActive(product.getIsActive());
        return responseDTO;
    }

    public Product toProductModel(ProductRequestDTO productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        return product;
    }
}
