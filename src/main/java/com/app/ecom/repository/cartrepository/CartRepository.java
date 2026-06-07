package com.app.ecom.repository.cartrepository;

import com.app.ecom.model.cart.Cart;
import com.app.ecom.model.user.User;
import org.hibernate.boot.jaxb.mapping.spi.JaxbPersistentAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long id);
}
