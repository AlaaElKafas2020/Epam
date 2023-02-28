package com.example.cloud.service.impl.dao.impl;

import com.example.cloud.service.impl.dao.UserDao;
import com.example.dto.User;

import java.util.ArrayList;
import java.util.List;

public class MemoryUserDaoImpl implements UserDao {

	private final List<User> users;

	public MemoryUserDaoImpl() {
		this.users = new ArrayList<>();
	}

	public void addUser(User user) {
		users.add(user);
	}

	public List<User> getAllUsers() {
		return List.copyOf(users);
	}


}
