package com.atlavik.challenge.common;

import com.atlavik.challenge.dao.model.Cart;
import com.atlavik.challenge.dao.model.Product;
import com.atlavik.challenge.dto.CartDto;
import com.atlavik.challenge.dto.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.List;

public class TestUtils<T> {

    public static final int EXISTING_CART_ID = 1;
    public static final int NON_EXISTING_CART_ID = 2;
    public static final int EXISTING_PRODUCT_ID = 1;
    public static final int NON_EXISTING_PRODUCT_ID = 2;

    public static final String BASE_URL = "http://localhost:8080/api";
    public static final String NEW_CART_URI = BASE_URL + "/carts/";
    public static final String GET_CART_URI = BASE_URL + "/carts?cartId=";
    public static final String PRODUCT_URI = BASE_URL + "/carts/";
    public static final String GET_PRODUCT_URI = "/products?productId=";
    public static final String PUT_PRODUCT_URI = "/products";

    public static Cart newCart() {
        Cart cart = new Cart();

        cart.setId(EXISTING_CART_ID);
        cart.setCountryCode("US");
        cart.setCurrency("USD");
        cart.setUpdated(new Date());
        cart.setCreated(new Date());
        cart.setProducts(null);
        return cart;
    }
    public static Product newProduct() {
        Product product = new Product();

        product.setId(EXISTING_PRODUCT_ID);
        product.setPrice(55.6F);
        product.setUpdated(new Date());
        product.setCreated(new Date());
        product.setDescription("Apple iPhone 12");
        product.setCategory("ELECTRONICS");
        return product;
    }
    public static ProductDto newProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setPrice(55.6F);
        productDto.setDescription("Apple iPhone 12");
        productDto.setCategory("ELECTRONICS");
        return productDto;
    }

    public static CartDto newCartDto() {
        CartDto cartDto = new CartDto();
        cartDto.setCurrency("USD");
        cartDto.setCountryCode("US");
        return cartDto;
    }

    public String asJsonString(T cartDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(cartDto);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

}
