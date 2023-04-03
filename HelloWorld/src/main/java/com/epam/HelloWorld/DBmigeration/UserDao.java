package com.epam.HelloWorld.DBmigeration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epam.HelloWorld.Security.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;

public class UserDao {

	private BasicDataSource dataSource;

	public UserDao() {
		dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:mysql://localhost:3306/mydatabase");
		dataSource.setUsername("myuser");
		dataSource.setPassword("mypassword");

		// Initialize Flyway for database migrations
		Flyway flyway = Flyway.configure()
				.dataSource(dataSource)
				.locations("db/migration")
				.load();
		flyway.migrate();
	}

	public List<User> findAll() throws SQLException {
		List<User> users = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
			 ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setEmail(resultSet.getString("email"));
				// set other properties of User object
				users.add(user);
			}
		}
		return users;
	}

	// Implement other CRUD methods (findById, save, update, delete) using similar approach as findAll
}