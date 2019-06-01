package com.liceolapaz.secondhandmarket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liceolapaz.secondhandmarket.models.Purchase;
import com.liceolapaz.secondhandmarket.models.User;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

	List<Purchase> findByBuyer(User buyer);

}