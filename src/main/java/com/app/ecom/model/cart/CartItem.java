package com.app.ecom.model.cart;

import com.app.ecom.model.product.Product;
import com.app.ecom.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name="cart_id", nullable = false)
    private Cart cart;
    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
