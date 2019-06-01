package com.liceolapaz.secondhandmarket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liceolapaz.secondhandmarket.models.Product;
import com.liceolapaz.secondhandmarket.models.Purchase;
import com.liceolapaz.secondhandmarket.models.User;
import com.liceolapaz.secondhandmarket.repositories.ProductRepository;
import com.liceolapaz.secondhandmarket.storage.StorageService;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	StorageService storageService;

	public Product insert(Product p) {
		return productRepository.save(p);
	}

	public void delete(long id) {
		Product product = productRepository.getOne(id);
		if (!product.getImage().isEmpty())
			storageService.delete(product.getImage());
		productRepository.deleteById(id);
	}

	public void delete(Product product) {
		if (!product.getImage().isEmpty())
			storageService.delete(product.getImage());
		productRepository.delete(product);
	}

	public Product edit(Product product) {
		return productRepository.save(product);
	}

	public Product findById(long id) {
		return productRepository.findById(id).orElse(null);
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public List<Product> productsByOwner(User user) {
		return productRepository.findByOwner(user);
	}

	public List<Product> productsByPurchase(Purchase purchase) {
		return productRepository.findByPurchase(purchase);
	}

	public List<Product> productsNotSold() {
		return productRepository.findByPurchaseIsNull();
	}

	public List<Product> search(String query) {
		return productRepository.findByNameContainsIgnoreCaseAndPurchaseIsNull(query);
	}

	public List<Product> searchMyProducts(String query, User user) {
		return productRepository.findByNameContainsIgnoreCaseAndOwner(query, user);
	}

	public List<Product> findAllById(List<Long> ids) {
		return productRepository.findAllById(ids);
	}

}
