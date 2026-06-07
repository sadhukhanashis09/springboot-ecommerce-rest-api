package com.app.ecom.dto.orderdto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long orderId;
    private Long userId;
    private List<OrderItemResponseDTO> items;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;
}
