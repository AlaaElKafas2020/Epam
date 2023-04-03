package com.epam.HelloWorld.Security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// this code created by Alaa and this class should be existed in controller package but i will put all task's classes here
@RestController
@RequestMapping("/api")
public class UserController {

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		//get all users
		return null;
	}

	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		//create a user
		return null;
	}
}


