package com.atlavik.challenge.repository;

import com.atlavik.challenge.common.TestUtils;
import com.atlavik.challenge.dao.model.Product;
import com.atlavik.challenge.dao.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    Product savedProduct;

    /**
     * First scenario: Just save a Product then fetch from database
     * Second scenario: fetch a non existing product
     */
    @Test
    void saveAndFindCartTest() {
        Product productDefault = TestUtils.newProduct();

        savedProduct = productRepository.save(productDefault);
        assertNotNull(savedProduct);

        Optional<Product> possibleProduct = productRepository.findById(savedProduct.getId());

        assertTrue(possibleProduct.isPresent());

        Product product = possibleProduct.get();
        assertEquals(TestUtils.EXISTING_PRODUCT_ID, productDefault.getId());
        assertEquals("ELECTRONICS", product.getCategory());
        assertEquals("Apple iPhone 12", product.getDescription());
    }

    /**
     * Clear database after test
     */
    @AfterEach
    void clearProductSaved() {
        productRepository.deleteById(savedProduct.getId());
    }


}
