package com.app.ecom.repository.cartitemrepository;

import com.app.ecom.model.cart.Cart;
import com.app.ecom.model.cart.CartItem;
import com.app.ecom.model.product.Product;
import com.app.ecom.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartAndProduct(Cart cart, Product product);

    void deleteByCartAndProduct(Cart cart, Product product);
}
