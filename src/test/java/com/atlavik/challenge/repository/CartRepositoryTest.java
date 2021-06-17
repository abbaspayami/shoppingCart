package com.atlavik.challenge.repository;

import com.atlavik.challenge.common.TestUtils;
import com.atlavik.challenge.dao.model.Cart;
import com.atlavik.challenge.dao.repository.CartRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Data Layer testing
 *
 * @author Abbas
 */
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    Cart cartSaved;

    /**
     * First scenario: Just save a cart then fetch from database
     * Second scenario: fetch a non existing cart
     */
    @Test
    void saveAndFindCartTest() {
        Cart cartDefault = TestUtils.newCart();

        cartSaved = cartRepository.save(cartDefault);
        assertNotNull(cartSaved);

        Optional<Cart> possibleCart = cartRepository.findById(cartSaved.getId());

        assertTrue(possibleCart.isPresent());

        Cart cart = possibleCart.get();
        assertEquals(TestUtils.EXISTING_CART_ID, cartDefault.getId());
        assertEquals("USD", cart.getCurrency());
        assertEquals("US", cart.getCountryCode());
    }

    /**
     * Clear database after test
     */
    @AfterEach
    void clearCartSaved() {
        cartRepository.deleteById(cartSaved.getId());
    }

}
