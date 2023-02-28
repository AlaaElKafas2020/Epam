package com.example.cloud.service.impl.dao.impl;

import com.example.cloud.service.impl.dao.SubscriptionDao;
import com.example.dto.Subscription;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemorySubscriptionDaoImpl implements SubscriptionDao {

	private final List<Subscription> subscriptions;

	public MemorySubscriptionDaoImpl() {
		this.subscriptions = new ArrayList<>();
	}

	public void subscribe(String cardNumber) {
		if (cardNumber != null) {
			subscriptions.removeIf(subscription -> subscription.getBankCard().equals(cardNumber));
			subscriptions.add(new Subscription(cardNumber, LocalDate.now()));
		}
	}

	public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
		return subscriptions.stream()
				.filter(subscription -> subscription.getBankCard().equals(cardNumber))
				.findAny();
	}

	public List<Subscription> getAllSubscriptions() {
		return List.copyOf(subscriptions);
	}

}
