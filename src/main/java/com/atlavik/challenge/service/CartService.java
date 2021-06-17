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
    public Cart create(CartDto cartDto) {

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
        log.debug("Get Product Method, cartId {}", cartId);
        return checkCartIsExist(cartId);
    }

    /**
     *  Finding Product
     * @param cartId   to find Cart
     * @param productId to find Product
     * @return ShoppingCart Object
     */
    public Product getProduct(Integer cartId, Integer productId) {
        log.debug("Get Product Method, cartId {}, productId {}", cartId, productId);
        return checkProductIsExist(cartId, productId);
    }

    /**
     *  Updating Product
     * @param cartId   cart Id
     * @param productDto productDto input
     * @return   Product Object
     */
    public Product updateProduct(Integer cartId, ProductDto productDto) {
        Cart cart = checkCartIsExist(cartId);

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
        checkProductIsExist(cartId, productId);
        productRepository.removeProductByIdAndCart_Id(productId, cartId);
    }

    /**
     * checking cart is exist
     * @param cartId to find cart object
     * @return Cart object
     */
    public Cart checkCartIsExist(Integer cartId) {
        log.debug("checkCartIsExist : cartId {}", cartId);
        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        if (!optionalCart.isPresent())
            throw new CartNotFoundException("Cart Not Found.");

        log.debug("cart found with id {}", cartId);
        return optionalCart.get();
    }

    /**
     *  checking product is exist
     * @param cartId  cartId
     * @param productId productId
     * @return Product object
     */
    public Product checkProductIsExist(Integer cartId, Integer productId) {
        log.debug("Check Product Is Exist, cartId {}, productId {}", cartId, productId);
        Optional<Product> possibleProduct = productRepository.findByIdAndCart_Id(productId, cartId);

        if (!possibleProduct.isPresent())
            throw new ProductNotFoundException("Product Not Found.");

        log.debug("product found with id {}", productId);
        return possibleProduct.get();
    }


}
