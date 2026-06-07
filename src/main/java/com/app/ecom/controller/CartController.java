package com.app.ecom.controller;

import com.app.ecom.dto.cartdto.CartItemRequestDTO;
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
    public ResponseEntity<Void> addProductToCart(
            @RequestHeader ("X-User-Id") String userId,
            @RequestBody CartItemRequestDTO cartItemRequest
    ){
        if(!cartService.addProductToCart(userId, cartItemRequest)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
