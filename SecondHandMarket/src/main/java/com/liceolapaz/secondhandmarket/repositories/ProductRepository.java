package com.liceolapaz.secondhandmarket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liceolapaz.secondhandmarket.models.Product;
import com.liceolapaz.secondhandmarket.models.Purchase;
import com.liceolapaz.secondhandmarket.models.User;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByOwner(User owner);

	List<Product> findByPurchase(Purchase purchase);

	List<Product> findByPurchaseIsNull();

	List<Product> findByNameContainsIgnoreCaseAndPurchaseIsNull(String name);

	List<Product> findByNameContainsIgnoreCaseAndOwner(String name, User owner);

}