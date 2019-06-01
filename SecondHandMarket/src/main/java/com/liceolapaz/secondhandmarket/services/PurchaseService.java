package com.liceolapaz.secondhandmarket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liceolapaz.secondhandmarket.models.Product;
import com.liceolapaz.secondhandmarket.models.Purchase;
import com.liceolapaz.secondhandmarket.models.User;
import com.liceolapaz.secondhandmarket.repositories.PurchaseRepository;

@Service
public class PurchaseService {

	@Autowired
	PurchaseRepository purchaseRepository;
	
	@Autowired
	ProductService productService;
	
	public Purchase insert(Purchase purchase, User user) {
		purchase.setBuyer(user);
		return purchaseRepository.save(purchase);
	}
	
	public Purchase insert(Purchase c) {
		return purchaseRepository.save(c);
	}
	
	public Product addProduct(Product product, Purchase purchase) {
		product.setPurchase(purchase);
		return productService.edit(product);
	}
	
	public Purchase findById(long id) {
		return purchaseRepository.findById(id).orElse(null);
	}
	
	public List<Purchase> findAll() {
		return purchaseRepository.findAll();
	}
	
	public List<Purchase> findByBuyer(User user) {
		return purchaseRepository.findByBuyer(user);
	}
	
}
