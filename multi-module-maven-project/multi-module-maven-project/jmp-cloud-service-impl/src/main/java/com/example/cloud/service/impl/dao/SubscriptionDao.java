package com.example.cloud.service.impl.dao;

import com.example.dto.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionDao {

	void subscribe(String cardNumber);

	Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);

	List<Subscription> getAllSubscriptions();

}
