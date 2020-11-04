package com.example.login.service;

import java.util.List;

import com.example.login.entity.User;

public interface AccountService {

	List<User> findAll();
	void register(String name, String email, String password, String[] roles);
}
