package com.atlavik.challenge.dao.repository;

import com.atlavik.challenge.dao.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    void removeProductByIdAndCart_Id(Integer id, Integer cartId);
    Optional<Product> findByIdAndCart_Id(Integer id, Integer cartId);
}
