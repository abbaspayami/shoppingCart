package com.atlavik.challenge.service;

import com.atlavik.challenge.dao.model.Cart;
import com.atlavik.challenge.dao.model.Product;
import com.atlavik.challenge.dao.repository.CartRepository;
import com.atlavik.challenge.dao.repository.ProductRepository;
import com.atlavik.challenge.dto.CartDto;
import com.atlavik.challenge.dto.ProductDto;
import com.atlavik.challenge.exception.CartNotFoundException;
import com.atlavik.challenge.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * The Shopping Cart logic implementation,
 * All changes will rollback by any exception
 *
 * @author Abbas
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    /**
     * Create Shopping Cart
     * @param cartDto cartDto input
     * @return Cart Saved Object
     */
    public Cart createShoppingCart(CartDto cartDto) {

        Cart cart = new Cart();
        cart.setCurrency(cartDto.getCurrency());
        cart.setCountryCode(cartDto.getCountryCode());
        cart.setCreated(new Date());
        cart.setUpdated(new Date());
        return cartRepository.save(cart);
    }

    /**
     * Finding All Cart
     * @return Cart Object
     */
    public Cart getShoppingCart(Integer cartId) {
        log.debug("Get Product Method with cartId {}", cartId);
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        return optionalCart.orElseThrow(() ->{
            log.debug("CartNotFoundException with cartId {}", cartId);
            return new CartNotFoundException("Cart Not Found.");
        });
    }

    /**
     *  Finding Product
     * @param cartId   to find Cart
     * @param productId to find Product
     * @return ShoppingCart Object
     */
    public Product getProduct(Integer cartId, Integer productId) {
        log.debug("Get Product Method with cartId {}, productId {}", cartId, productId);
        Optional<Product> possibleProduct = productRepository.findByIdAndCart_Id(productId, cartId);
        return possibleProduct.orElseThrow(() -> {
            log.debug("ProductNotFoundException with cartId {}, productId {}", cartId, productId);
            return new ProductNotFoundException("Product Not Found.");
        });
    }

    /**
     *  Updating Product
     * @param cartId   cart Id
     * @param productDto productDto input
     * @return   Product Object
     */
    public Product updateProduct(Integer cartId, ProductDto productDto) {
        Cart cart = getShoppingCart(cartId);

        Product product = new Product();
        product.setCart(cart);
        product.setCategory(productDto.getCategory());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCreated(new Date());
        product.setUpdated(new Date());

        return productRepository.save(product);
    }

    /**
     * Deleting Product
     * @param cartId   cart id
     * @param productId product id
     */
    public void removeProduct(Integer cartId, Integer productId) {
        log.debug("Delete Product in cartId {} and productId {}", cartId, productId);
        getProduct(cartId, productId);
        productRepository.removeProductByIdAndCart_Id(productId, cartId);
    }


}
