package com.liceolapaz.secondhandmarket.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.liceolapaz.secondhandmarket.models.Product;
import com.liceolapaz.secondhandmarket.models.User;
import com.liceolapaz.secondhandmarket.services.ProductService;
import com.liceolapaz.secondhandmarket.services.UserService;
import com.liceolapaz.secondhandmarket.storage.StorageService;

@Controller
@RequestMapping("/app")
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;

	@Autowired
	StorageService storageService;

	private User user;

	@ModelAttribute("myproducts")
	public List<Product> myProducts() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		user = userService.findByEmail(email);
		return productService.productsByOwner(user);
	}

	@GetMapping("/myproducts")
	public String list(Model model, @RequestParam(name = "q", required = false) String query) {
		if (query != null)
			model.addAttribute("myproducts", productService.searchMyProducts(query, user));

		return "app/product/list";
	}

	@GetMapping("/myproducts/{id}/delete")
	public String delete(@PathVariable Long id) {
		Product product = productService.findById(id);
		if (product.getPurchase() == null)
			productService.delete(product);
		return "redirect:/app/myproducts";
	}

	@GetMapping("/product/new")
	public String newProductForm(Model model) {
		model.addAttribute("product", new Product());
		return "app/product/form";
	}

	@PostMapping("/product/new/submit")
	public String newProductSubmit(@ModelAttribute Product product, @RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			String image = storageService.store(file);
			product.setImage(MvcUriComponentsBuilder.fromMethodName(FilesController.class, "serveFile", image).build()
					.toUriString());
		}
		product.setOwner(user);
		productService.insert(product);
		return "redirect:/app/myproducts";
	}

}