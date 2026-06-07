package com.app.ecom.dto.cartdto.request;

import lombok.Data;

@Data
public class CartItemRequestDTO {
    private Long productId;
    private Integer quantity;
}
