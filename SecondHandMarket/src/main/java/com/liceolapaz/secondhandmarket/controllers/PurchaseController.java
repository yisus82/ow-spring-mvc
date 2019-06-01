package com.liceolapaz.secondhandmarket.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liceolapaz.secondhandmarket.models.Product;
import com.liceolapaz.secondhandmarket.models.Purchase;
import com.liceolapaz.secondhandmarket.models.User;
import com.liceolapaz.secondhandmarket.services.ProductService;
import com.liceolapaz.secondhandmarket.services.PurchaseService;
import com.liceolapaz.secondhandmarket.services.UserService;

@Controller
@RequestMapping("/app")
public class PurchaseController {

	@Autowired
	PurchaseService purchaseService;

	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;

	@Autowired
	HttpSession session;

	private User user;

	@SuppressWarnings("unchecked")
	@ModelAttribute("cart")
	public List<Product> cartProducts() {
		List<Long> content = (List<Long>) session.getAttribute("cart");
		return (content == null) ? null : productService.findAllById(content);
	}

	@ModelAttribute("total_cart")
	public Double totalCart() {
		List<Product> cartProducts = cartProducts();
		if (cartProducts != null)
			return cartProducts.stream().mapToDouble(product -> product.getPrice()).sum();
		return 0.0;
	}

	@ModelAttribute("mypurchases")
	public List<Purchase> myPurchases() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		user = userService.findByEmail(email);
		return purchaseService.findByBuyer(user);
	}

	@GetMapping("/cart")
	public String verCarrito(Model model) {
		return "app/purchase/cart";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/cart/add/{id}")
	public String addToCart(Model model, @PathVariable Long id) {
		List<Long> content = (List<Long>) session.getAttribute("cart");
		if (content == null)
			content = new ArrayList<>();
		if (!content.contains(id))
			content.add(id);
		session.setAttribute("cart", content);
		return "redirect:/app/cart";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/cart/remove/{id}")
	public String removeFromCart(Model model, @PathVariable Long id) {
		List<Long> content = (List<Long>) session.getAttribute("cart");
		if (content == null)
			return "redirect:/public";
		content.remove(id);
		if (content.isEmpty())
			session.removeAttribute("cart");
		else
			session.setAttribute("cart", content);
		return "redirect:/app/cart";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/cart/checkout")
	public String checkout() {
		List<Long> content = (List<Long>) session.getAttribute("cart");
		if (content == null)
			return "redirect:/public";

		List<Product> products = cartProducts();

		Purchase purchase = purchaseService.insert(new Purchase(), user);

		products.forEach(product -> purchaseService.addProduct(product, purchase));
		session.removeAttribute("cart");

		return "redirect:/app/purchase/invoice/" + purchase.getId();
	}

	@GetMapping("/mypurchases")
	public String myPurchases(Model model) {
		return "/app/purchase/list";
	}

	@GetMapping("/purchase/invoice/{id}")
	public String invoice(Model model, @PathVariable Long id) {
		Purchase purchase = purchaseService.findById(id);
		List<Product> products = productService.productsByPurchase(purchase);
		model.addAttribute("products", products);
		model.addAttribute("purchase", purchase);
		model.addAttribute("total_purchase", products.stream().mapToDouble(product -> product.getPrice()).sum());
		return "/app/purchase/invoice";
	}

}