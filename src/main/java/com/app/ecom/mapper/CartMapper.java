package com.app.ecom.mapper;

import com.app.ecom.dto.cartdto.response.CartItemResponseDTO;
import com.app.ecom.dto.cartdto.response.CartResponseDTO;
import com.app.ecom.model.cart.Cart;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CartMapper {
    public CartResponseDTO mapToDTO(Cart cart) {
        List<CartItemResponseDTO> itemDTOs = cart.getItems().stream()
                .map(item -> CartItemResponseDTO.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .subtotal(item.getSubtotal())
                        .build())
                .toList();

        BigDecimal totalAmount = itemDTOs.stream()
                .map(CartItemResponseDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponseDTO.builder()
                .cartId(cart.getId())
                .items(itemDTOs)
                .totalAmount(totalAmount)
                .build();
    }
}
