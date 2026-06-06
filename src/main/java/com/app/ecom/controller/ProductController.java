package com.app.ecom.controller;

import com.app.ecom.dto.productdto.ProductRequestDTO;
import com.app.ecom.dto.productdto.ProductResponseDTO;
import com.app.ecom.service.productservice.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    //Get All Product
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    //Get Active Product
    @GetMapping("/active")
    public ResponseEntity<List<ProductResponseDTO>> getActiveProducts() {
        return ResponseEntity.ok(productService.getActiveProduct());
    }
    //Get Product By Id
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(
            @PathVariable Long id) {
        ProductResponseDTO productResponse = productService.getProductById(id);
        if (productResponse != null) {
            return ResponseEntity.ok(productResponse);
        }
        return ResponseEntity.notFound().build();
    }

    //Product search
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> getProductByKeyWords(
            @RequestParam String keywords) {
        List<ProductResponseDTO> productResponse = productService.searchProducts(keywords);
        if (productResponse != null) {
            return ResponseEntity.ok(productResponse);
        }
        return ResponseEntity.notFound().build();
    }

    //Create Product
    @PostMapping()
    public ResponseEntity<String> createProduct(
            @RequestBody ProductRequestDTO productRequest
    ) {
        ProductResponseDTO createdProduct = productService.createProduct(productRequest);
        URI location = URI.create("/api/products/" + createdProduct.getId());

        return ResponseEntity
                .created(location)
                .body("Product created Successfully at:"+ location.toString());
    }

    //Update an existing product
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id, @RequestBody ProductRequestDTO
            productUpdateRequest
    ) {
        ProductResponseDTO updatedProduct = productService.updateProduct(id, productUpdateRequest);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        boolean isDeleted = productService.deleteById(id);
        if(isDeleted){
            return ResponseEntity.ok("Product deleted Successfully");
        }
        return ResponseEntity.notFound().build();
    }
}
