package com.atlavik.challenge.service;

import com.atlavik.challenge.common.TestUtils;
import com.atlavik.challenge.dao.model.Cart;
import com.atlavik.challenge.dao.model.Product;
import com.atlavik.challenge.dao.repository.CartRepository;
import com.atlavik.challenge.dao.repository.ProductRepository;
import com.atlavik.challenge.dto.CartDto;
import com.atlavik.challenge.dto.ProductDto;
import com.atlavik.challenge.exception.CartNotFoundException;
import com.atlavik.challenge.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Testing business logic layer
 *
 * @author Abbas
 */
@ExtendWith(SpringExtension.class)
class CartServiceTest {


    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private ProductRepository productRepository;

    private CartService cartService;

    private Cart cart;

    private Product product;

    /**
     * run Before Each test
     */
    @BeforeEach
    void setUp() {
        cartService = new CartService(cartRepository, productRepository);

        cart = TestUtils.newCart();
        product = TestUtils.newProduct();

        when(productRepository.findByIdAndCart_Id(TestUtils.EXISTING_CART_ID, TestUtils.EXISTING_PRODUCT_ID)).thenReturn(Optional.of(product));
        when(productRepository.findByIdAndCart_Id(TestUtils.EXISTING_CART_ID, TestUtils.NON_EXISTING_PRODUCT_ID)).thenReturn(Optional.empty());
        when(cartRepository.findById(TestUtils.EXISTING_CART_ID)).thenReturn(Optional.of(cart));
        when(cartRepository.findById(TestUtils.NON_EXISTING_CART_ID)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(productRepository.save(any(Product.class))).thenReturn(product);

    }

    /**
     * loading existing product
     */
    @Test
    void load_Existing_ProductTest() {
        Product product = cartService.getProduct(TestUtils.EXISTING_CART_ID, TestUtils.EXISTING_PRODUCT_ID);

        assertNotNull(product);
        assertEquals(TestUtils.EXISTING_PRODUCT_ID, product.getId());
    }

    /**
     * loading Non existing product
     */
    @Test
    void load_NonExisting_ProductTest() {
        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            cartService.getProduct(TestUtils.EXISTING_CART_ID, TestUtils.NON_EXISTING_PRODUCT_ID);
        });
        String expectedMessage = "Product Not Found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * loading existing cart
     */
    @Test
    void load_Existing_ShoppingCartTest() {
        Cart cart = cartService.getShoppingCart(TestUtils.EXISTING_CART_ID);

        assertNotNull(cart);
        assertEquals(TestUtils.EXISTING_CART_ID, cart.getId());
    }

    /**
     * checking Non existing cart
     */
    @Test
    void load_NonExisting_ShoppingCartTest() {
        Exception exception = assertThrows(CartNotFoundException.class, () -> {
            cartService.getShoppingCart(TestUtils.NON_EXISTING_CART_ID);
        });
        String expectedMessage = "Cart Not Found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Creating new cart test
     */
    @Test
    void create_ShoppingCartTest() {
        CartDto cartDto = TestUtils.newCartDto();
        cartService.create(cartDto);

        assertNotNull(cart);
        assertEquals(TestUtils.EXISTING_CART_ID, cart.getId());
        assertEquals(cartDto.getCountryCode(), cart.getCountryCode());
        assertEquals(cartDto.getCurrency(), cart.getCurrency());

    }

    @Test
    void updateProductTest() {
        ProductDto productDto = TestUtils.newProductDto();
        cartService.updateProduct(TestUtils.EXISTING_CART_ID, productDto);

        assertNotNull(product);
        assertEquals(TestUtils.EXISTING_PRODUCT_ID, product.getId());
        assertEquals(productDto.getCategory(), product.getCategory());
        assertEquals(productDto.getPrice(), product.getPrice());
    }

//    @Test
//    void delete_ProductTest() {
//        cartService.deleteProduct(TestUtils.EXISTING_CART_ID, TestUtils.EXISTING_PRODUCT_ID);
//
//    }



}
