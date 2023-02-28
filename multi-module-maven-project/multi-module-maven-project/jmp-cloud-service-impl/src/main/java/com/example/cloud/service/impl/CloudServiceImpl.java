package com.example.cloud.service.impl;

import com.example.cloud.service.impl.dao.SubscriptionDao;
import com.example.cloud.service.impl.dao.UserDao;
import com.example.cloud.service.impl.dao.impl.MemorySubscriptionDaoImpl;
import com.example.cloud.service.impl.dao.impl.MemoryUserDaoImpl;
import com.example.dto.BankCard;
import com.example.dto.Subscription;
import com.example.dto.User;
import com.example.service.api.Service;
import com.example.service.api.exception.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ServiceLoader;
import java.util.function.Predicate;

public class CloudServiceImpl implements Service {

	private final UserDao userDao;

	private final SubscriptionDao subscriptionDao;

	public CloudServiceImpl() {//TODO
		var userDaoServiceLoader = ServiceLoader.load(UserDao.class, UserDao.class.getClassLoader());
		this.userDao = userDaoServiceLoader.findFirst().orElse(new MemoryUserDaoImpl());

		var subscriptionDaoServiceLoader = ServiceLoader.load(SubscriptionDao.class, SubscriptionDao.class.getClassLoader());
		this.subscriptionDao = subscriptionDaoServiceLoader.findFirst().orElse(new MemorySubscriptionDaoImpl());
	}

	@Override
	public void subscribe(BankCard card) {
		subscriptionDao.subscribe(card.getNumber());
	}

	@Override
	public Subscription getSubscriptionByBankCardNumber(String cardNumber) {
		return subscriptionDao.getSubscriptionByBankCardNumber(cardNumber)
				.orElseThrow(() -> new EntityNotFoundException("This Card number not existed : " + cardNumber));
	}

	@Override
	public List<Subscription> getAllSubscriptions() {
		return subscriptionDao.getAllSubscriptions();
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public void addUser(User user) {
		userDao.addUser(user);

	}

	@Override
	public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition) {
		return subscriptionDao.getAllSubscriptions()
				.stream()
				.filter(condition::test)
				.collect(Collectors.toUnmodifiableList());
	}

}
