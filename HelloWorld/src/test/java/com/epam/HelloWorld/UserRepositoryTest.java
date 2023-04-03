package com.epam.HelloWorld;

import static org.assertj.core.api.Assertions.assertThat;

import com.epam.HelloWorld.Security.User;
import com.epam.HelloWorld.repos.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testFindAll() {
		List<User> users = userRepository.findAll();
		assertThat(users).hasSize(0);
	}

	@Test
	public void testSave() {
		User user = new User();
		user.setUsername("Alaa");
		user.setPassword("password");
		user.setEmail("Alaa@example.com");
		userRepository.save(user);
		List<User> users = userRepository.findAll();
		assertThat(users).hasSize(1);
		assertThat(users.get(0).getUsername()).isEqualTo("Alaa");
	}

	@Test
	public void testFindByUsername() {
		User user = new User();
		user.setUsername("Alaa");
		user.setPassword("password");
		user.setEmail("Alaa@example.com");
		userRepository.save(user);
		User retrievedUser = userRepository.findByUsername("Alaa");
		assertThat(retrievedUser).isNotNull();
		assertThat(retrievedUser.getUsername()).isEqualTo("Alaa");
	}
}
