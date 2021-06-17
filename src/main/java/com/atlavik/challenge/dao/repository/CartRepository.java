package com.atlavik.challenge.dao.repository;

import com.atlavik.challenge.dao.model.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {
}
