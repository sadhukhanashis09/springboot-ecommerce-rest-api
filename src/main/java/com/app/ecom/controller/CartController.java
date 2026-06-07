package com.app.ecom.controller;

import com.app.ecom.dto.cartdto.request.*;
import com.app.ecom.dto.cartdto.response.CartResponseDTO;
import com.app.ecom.service.cartservice.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartResponseDTO> addProductToCart(
            @RequestHeader ("X-User-Id") String userId,
            @RequestBody CartItemRequestDTO cartItemRequest
    ){
        CartResponseDTO response = cartService.addProductToCart(userId, cartItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDTO> getCartItems(@PathVariable String userId) {
        CartResponseDTO response = cartService.getCartItems(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/cart/item/{productId}")
    public ResponseEntity<Void> deleteCartItem(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable("productId") String productId
    ) {
        boolean isDeleted = cartService.deleteCartItem(userId, productId);

        return isDeleted
                ? ResponseEntity.noContent().build()   // 204 No Content
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
    }


}
