package com.liceolapaz.secondhandmarket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.liceolapaz.secondhandmarket.models.User;
import com.liceolapaz.secondhandmarket.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	public User register(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public User findById(long id) {
		return userRepository.findById(id).orElse(null);
	}

	public User findByEmail(String email) {
		return userRepository.findFirstByEmail(email);
	}

}
