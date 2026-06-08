package com.app.ecom.service.orderservice;

import com.app.ecom.dto.orderdto.request.OrderItemRequestDTO;
import com.app.ecom.dto.orderdto.response.OrderItemResponseDTO;
import com.app.ecom.dto.orderdto.response.OrderResponseDTO;
import com.app.ecom.model.cart.Cart;
import com.app.ecom.model.order.Order;
import com.app.ecom.model.order.OrderItem;
import com.app.ecom.model.order.OrderStatus;
import com.app.ecom.model.product.Product;
import com.app.ecom.model.user.User;
import com.app.ecom.repository.cartrepository.CartRepository;
import com.app.ecom.repository.orderrepository.OrderItemRepository;
import com.app.ecom.repository.orderrepository.OrderRepository;
import com.app.ecom.repository.productrepository.ProductRepository;
import com.app.ecom.repository.userrepository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserRepository userRepository, ProductRepository productRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }


    public OrderResponseDTO placeOrder(Long userId, List<OrderItemRequestDTO> items) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User Not Found")
        );
        Order order = new Order();
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequestDTO orderItemRequestDTO : items) {
            Product product = productRepository.findById(orderItemRequestDTO.getProductId()).orElseThrow(
                    () -> new RuntimeException("Product Not found")
            );
            if (product.getStockQuantity() < orderItemRequestDTO.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderItem.setQuantity(orderItemRequestDTO.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequestDTO.getQuantity())));
            total = total.add(orderItem.getSubtotal());
            product.setStockQuantity(product.getStockQuantity() - orderItem.getQuantity());
            productRepository.save(product);
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);


        return mapToOrderResponseDTO(savedOrder);
    }


    public OrderResponseDTO placeOrderFromCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User Not Found")
        );

        Cart cart = cartRepository.findByUserId(userId).orElseThrow(
                () -> new RuntimeException("Cart Not Found")
        );
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }


        List<OrderItemRequestDTO> orderItemsFromCart = cart.getItems().stream().map(
                cartItemToOderItem -> new OrderItemRequestDTO(cartItemToOderItem.getId(), cartItemToOderItem.getQuantity())
        ).toList();


        OrderResponseDTO responseDTO = placeOrder(cart.getUser().getId(), orderItemsFromCart);

        /*for(CartItem cartItem:cart.getItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            orderItem.setSubtotal(cartItem.getSubtotal());
            total=total.add(orderItem.getSubtotal());
            orderItems.add(orderItem);
            Product product = new Product();
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);

        //Decrease Stock Quantity after Order Placed */


        cart.getItems().clear();
        cartRepository.save(cart);
        return responseDTO;

    }

    public List<OrderResponseDTO> getOrderByUser(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream().map(
                this::mapToOrderResponseDTO).toList();

       /*return orders.stream()
                .map(order -> mapToOrderResponseDTO(order)).toList();*/

    }

    public OrderResponseDTO getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(
                this::mapToOrderResponseDTO
        ).orElseThrow(() -> new RuntimeException("Order Not found"));
    }

    public OrderResponseDTO cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new RuntimeException("Order Not found")
        );
        order.setStatus(OrderStatus.CANCELLED);


        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productRepository.save(product);
        }
        Order cancelledOrder = orderRepository.save(order);
        return mapToOrderResponseDTO(cancelledOrder);
    }

    private OrderResponseDTO mapToOrderResponseDTO(Order savedOrder) {
        List<OrderItemResponseDTO> itemsDto = savedOrder.getItems().stream()
                .map(item -> OrderItemResponseDTO.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .subtotal(item.getSubtotal())
                        .build())
                .toList();
        return OrderResponseDTO.builder()
                .orderId(savedOrder.getId())
                .userId(savedOrder.getUser().getId())
                .items(itemsDto)
                .totalAmount(savedOrder.getTotalAmount())
                .status(savedOrder.getStatus().name())
                .createdAt(savedOrder.getCreatedAt())
                .build();
    }


}
