package com.atlavik.challenge.controller;

import com.atlavik.challenge.dao.model.Cart;
import com.atlavik.challenge.dao.model.Product;
import com.atlavik.challenge.dto.CartDto;
import com.atlavik.challenge.dto.ProductDto;
import com.atlavik.challenge.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Shopping Cart Rest endpoint
 * To create, findAll, and put, get, delete the product
 *
 * @author Abbas
 */
@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    /**
     * Create New Cart
     * @param cartDto cartDto
     * @return Cart Object
     */
    @PostMapping(value = "/carts")
    public ResponseEntity<Cart> createShoppingCart(@RequestBody CartDto cartDto) {
        return new ResponseEntity<>(cartService.createShoppingCart(cartDto), HttpStatus.CREATED);
    }

    /**
     * Finding Cart Object
     * @return Cart Object
     */
    @GetMapping(value = "/carts")
    public ResponseEntity<Cart> getShoppingCart(@RequestParam("cartId") int cartId) {
        log.debug("Get ShoppingCart By cartId {}", cartId);
        return new ResponseEntity<>(cartService.getShoppingCart(cartId), HttpStatus.OK);
    }

    /**
     * Find product based Id
     * @param cartId  cart Id
     * @param productId product Id
     * @return   Product Object
     */
    @GetMapping(value = "/carts/{cartId}/products")
    public ResponseEntity<Product> getProduct(@PathVariable final int cartId, @RequestParam("productId") int productId) {
        log.debug("Get Product cartId {} and productId {}", cartId, productId);
        return new ResponseEntity<>(cartService.getProduct(cartId, productId), HttpStatus.OK);
    }

    /**
     * Update product based Id
     * @param cartId cart Id
     * @param productDto  productDto input
     * @return   Product Cart
     */
    @PutMapping(value = "/carts/{cartId}/products")
    public ResponseEntity<Product> updateProduct(@PathVariable final int cartId, @RequestBody ProductDto productDto) {
        log.debug("Request Move cartId {} and products {}", cartId, productDto);
        return new ResponseEntity<>(cartService.updateProduct(cartId, productDto), HttpStatus.OK);
    }

    /**
     *  Remove product based Id
     * @param cartId  cart Id
     * @param productId product Id
     */
    @DeleteMapping(value = "/carts/{cartId}/products")
    public void removeProduct(@PathVariable final int cartId, @RequestParam("productId") int productId) {
        log.debug("Request Move cartId {} and productId {}", cartId, productId);
        cartService.removeProduct(cartId, productId);
        ResponseEntity.noContent();
    }

}
