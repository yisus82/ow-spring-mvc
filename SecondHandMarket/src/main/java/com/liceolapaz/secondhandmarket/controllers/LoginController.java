package com.liceolapaz.secondhandmarket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.liceolapaz.secondhandmarket.models.User;
import com.liceolapaz.secondhandmarket.services.UserService;
import com.liceolapaz.secondhandmarket.storage.StorageService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	StorageService storageService;

	@GetMapping("/")
	public String welcome() {
		return "redirect:/public/";
	}

	@GetMapping("/auth/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/auth/register")
	public String register(@ModelAttribute User user, @RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			String image = storageService.store(file);
			user.setAvatar(MvcUriComponentsBuilder.fromMethodName(FilesController.class, "serveFile", image).build()
					.toUriString());
		}
		userService.register(user);
		return "redirect:/auth/login";
	}

}