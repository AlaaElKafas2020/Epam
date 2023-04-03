package com.epam.HelloWorld.repos;

import com.epam.HelloWorld.Security.User;

import java.util.List;

public class UserRepository {
	User user= new User();
	public List<User> findAll() {
		return null;
	}

	public void save(User user) {
	}

	public User findByUsername(String username) {
		return user;
	}
}
