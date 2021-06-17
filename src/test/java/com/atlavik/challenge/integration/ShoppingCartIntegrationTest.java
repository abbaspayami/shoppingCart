package com.atlavik.challenge.integration;

import com.atlavik.challenge.common.TestUtils;
import com.atlavik.challenge.dao.model.Cart;
import com.atlavik.challenge.dao.model.Product;
import com.atlavik.challenge.dto.CartDto;
import com.atlavik.challenge.dto.ProductDto;
import com.atlavik.challenge.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
class ShoppingCartIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Cart cart;

    private Product product;

    /**
     * Create new cart before each test
     *
     * @throws Exception
     */
    @BeforeEach
    void createNewCart() throws Exception {
        TestUtils<CartDto> cartDtoTestUtils = new TestUtils<CartDto>();
        MvcResult mvcCartResult = mockMvc.perform(post(TestUtils.NEW_CART_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(cartDtoTestUtils.asJsonString(TestUtils.newCartDto())))
                .andExpect(status().isCreated())
                .andReturn();

        assertNotNull(mvcCartResult.getResponse());
        assertNotNull(mvcCartResult.getResponse().getContentAsString());

        cart = objectMapper.readValue(mvcCartResult.getResponse().getContentAsString(), Cart.class);
        assertNotNull(cart);
        assertNotEquals(0, cart.getId());

        TestUtils<ProductDto> productDtoTestUtils = new TestUtils<ProductDto>();
        MvcResult mvcProductResult = mockMvc.perform(put(TestUtils.PRODUCT_URI + cart.getId() + TestUtils.PUT_PRODUCT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(productDtoTestUtils.asJsonString(TestUtils.newProductDto())))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcProductResult);
        assertNotNull(mvcProductResult.getResponse().getContentAsString());

        product = objectMapper.readValue(mvcProductResult.getResponse().getContentAsString(), Product.class);
        assertNotNull(product);
        assertNotEquals(0, product.getId());
    }

    @Test
    void status_GetProduct_Test() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(TestUtils.PRODUCT_URI + cart.getId() + TestUtils.GET_PRODUCT_URI + product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult);
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    /**
     * checking the true Exception
     *
     * @throws Exception ProductNotFoundException if a product is not existing
     */
    @Test
    void statusNonExistingProduct() throws Exception {
        mockMvc.perform(get(TestUtils.PRODUCT_URI + cart.getId() + TestUtils.GET_PRODUCT_URI + 0))
                .andExpect(status().isNotFound());
    }

    /**
     * checking the true Exception
     *
     * @throws Exception CartNotFoundException if a Cart is not existing
     */
    @Test
    void statusNonExistingCart() throws Exception {
        mockMvc.perform(get(TestUtils.PRODUCT_URI + 0 + TestUtils.GET_PRODUCT_URI + product.getId()))
                .andExpect(status().isNotFound());
    }



    /**
     * Fetch an existing cart
     *
     * @throws Exception CartNotFoundException if a cart is not existing
     */
    @Test
    void statusExistingCartTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(TestUtils.GET_CART_URI + TestUtils.EXISTING_CART_ID))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult);
        assertNotNull(mvcResult.getResponse().getContentAsString());

    }

    @Test
    void statusDeleteProductTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete(TestUtils.PRODUCT_URI + cart.getId() + TestUtils.GET_PRODUCT_URI + product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult);
        assertNotNull(mvcResult.getResponse().getContentAsString());

    }

}
