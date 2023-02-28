package com.example.cloud.service.impl.dao;

import com.example.dto.User;

import java.util.List;

public interface UserDao {

	void addUser(User user);

	List<User> getAllUsers();
}
