package com.app.ecom.controller;

import com.app.ecom.dto.orderdto.request.OrderItemRequestDTO;
import com.app.ecom.dto.orderdto.response.OrderResponseDTO;
import com.app.ecom.service.orderservice.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<OrderResponseDTO> placeOrder(
            @PathVariable Long userId,
            @RequestBody List<OrderItemRequestDTO> items
    ) {
        OrderResponseDTO orderResponse = orderService.placeOrder(userId, items);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }
    @PostMapping("/from-cart/{userId}")
    public ResponseEntity<OrderResponseDTO> placeOrderFromCart(@PathVariable Long userId) {
        OrderResponseDTO response = orderService.placeOrderFromCart(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/user/{userId}")
        public ResponseEntity<List<OrderResponseDTO>> getOrderByUser(
                @PathVariable Long userId
        ){
            List<OrderResponseDTO> response = orderService.getOrderByUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @PathVariable Long orderId
    ){
        OrderResponseDTO response = orderService.getOrderById(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable Long orderId) {
        OrderResponseDTO cancelledOrder = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(cancelledOrder);
    }
}
