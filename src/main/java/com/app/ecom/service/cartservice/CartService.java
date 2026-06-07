package com.app.ecom.service.cartservice;

import com.app.ecom.dto.cartdto.request.CartItemRequestDTO;
import com.app.ecom.dto.cartdto.request.CartItemRequestDTO;
import com.app.ecom.dto.cartdto.response.CartItemResponseDTO;
import com.app.ecom.dto.cartdto.response.CartResponseDTO;
import com.app.ecom.mapper.CartMapper;
import com.app.ecom.model.cart.Cart;
import com.app.ecom.model.cart.CartItem;
import com.app.ecom.model.product.Product;
import com.app.ecom.model.user.User;
import com.app.ecom.repository.cartitemrepository.CartItemRepository;
import com.app.ecom.repository.cartrepository.CartRepository;
import com.app.ecom.repository.productrepository.ProductRepository;
import com.app.ecom.repository.userrepository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;

    public CartService(UserRepository userRepository, ProductRepository productRepository, CartRepository cartRepository, CartItemRepository cartItemRepository, CartMapper cartMapper) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartMapper = cartMapper;
    }

    // ✅ Add product to cart
    @Transactional
    public CartResponseDTO addProductToCart(String userId, CartItemRequestDTO cartItemRequest) {
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(cartItemRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + cartItemRequest.getQuantity());
            existingItem.setSubtotal(existingItem.getUnitPrice()
                    .multiply(BigDecimal.valueOf(existingItem.getQuantity())));
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(cartItemRequest.getQuantity());
            newItem.setUnitPrice(product.getPrice());
            newItem.setSubtotal(product.getPrice()
                    .multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            cart.getItems().add(newItem);
        }

        Cart savedCart = cartRepository.save(cart);
        //return mapToDTO(savedCart);
        return cartMapper.mapToDTO(savedCart);
    }

    // ✅ Get cart items
    public CartResponseDTO getCartItems(String userId) {
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return cartMapper.mapToDTO(cart);
    }

    // ✅ Delete cart item
    @Transactional
    public boolean deleteCartItem(String userId, String productId) {
        User user = userRepository.findById(Long.valueOf(userId)).orElse(null);
        Product product = productRepository.findById(Long.valueOf(productId)).orElse(null);

        if (user == null || product == null) return false;

        Cart cart = cartRepository.findByUserId(user.getId()).orElse(null);
        if (cart == null) return false;

        cartItemRepository.deleteByCartAndProduct(cart, product);
        return true;
    }
}


