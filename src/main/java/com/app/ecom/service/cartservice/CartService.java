package com.app.ecom.service.cartservice;

import com.app.ecom.dto.cartdto.CartItemRequestDTO;
import com.app.ecom.model.cart.CartItem;
import com.app.ecom.model.product.Product;
import com.app.ecom.model.user.User;
import com.app.ecom.repository.cartitemrepository.CartItemRepository;
import com.app.ecom.repository.productrepository.ProductRepository;
import com.app.ecom.repository.userrepository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public CartService(CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public boolean addProductToCart(String userId, CartItemRequestDTO cartItemRequest) {
        //first validation :Look for the product if exist using productId from cartItemRequest

        Optional<Product> productOpt = productRepository.findById(cartItemRequest.getProductId());


        if(productOpt.isEmpty()){
            return false;
        }
        //If product exist get the product
        Product product = productOpt.get();
        //Product stock validation
        if(product.getStockQuantity()<cartItemRequest.getQuantity()){
            return false;
        }
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()){
            return false;
        }
        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user,product);

        if(existingCartItem != null){
         //if Product already there Update the quantity
         existingCartItem.setQuantity(existingCartItem.getQuantity()+cartItemRequest.getQuantity());
         //Updating the price
         existingCartItem.setPrice(product.getPrice().multiply(
                 BigDecimal.valueOf(existingCartItem.getQuantity())
         ));
         cartItemRepository.save(existingCartItem);
        }
        else {
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemRequest.getQuantity());
        cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
        cartItemRepository.save(cartItem);
        }
        return true;


    }

    public boolean deleteCart(String userId, String productId) {
        Optional<Product> productOpt = productRepository.findById(Long.valueOf(productId));
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        if(productOpt.isPresent() && userOpt.isPresent()){
            cartItemRepository.deleteByUserAndProduct(userOpt.get(),productOpt.get());
            return true;
        }
        return false;
    }
}
