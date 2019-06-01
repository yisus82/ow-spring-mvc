package com.liceolapaz.secondhandmarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liceolapaz.secondhandmarket.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findFirstByEmail(String email);

}