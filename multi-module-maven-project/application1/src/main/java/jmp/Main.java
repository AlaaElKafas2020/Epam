package com.example.application;

import com.example.dto.Subscription;
import com.example.service.api.Service;
import com.example.service.api.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.example.application.Util.generateData;
import static com.example.application.Util.initServices;

public class Main {

	private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {

		initServices();
		generateData();

		var service = Util.getService();
		var bankCardList = Util.getBankCards();

		log.info(service.getAllUsers().toString());
		log.info(bankCardList.toString());
		log.info(service.getAllSubscriptions().toString());

		var existingCardNumber = bankCardList.get(0).getNumber();
		Subscription subscriptionByExistingNumber = service.getSubscriptionByBankCardNumber(existingCardNumber);
		log.info("Card found by card number " + existingCardNumber + ": " + subscriptionByExistingNumber);

		var nonExistingCardNumber = "1";
		try {
			service.getSubscriptionByBankCardNumber(nonExistingCardNumber);
		} catch (EntityNotFoundException e) {
			log.info("Card not found by nonexistent card number " + nonExistingCardNumber);
		}

		var payableUsers = service.getAllUsers()
				.stream()
				.filter(Service::isPayableUser)
				.collect(Collectors.toUnmodifiableList());

		log.info("Users that are 18 years old or older: " + payableUsers);

		var firstSubscription = service.getAllSubscriptions().get(0);
		firstSubscription.setStartDate(firstSubscription.getStartDate().minusMonths(2));

		Predicate<Subscription> olderThanOneMonth = subscription ->
				subscription.getStartDate().isBefore(LocalDate.now().minusMonths(1));

		var subscriptionsOlderThanOneMonth = service.getAllSubscriptionsByCondition(olderThanOneMonth);
		log.info("Subscriptions created more than 1 month ago: " + subscriptionsOlderThanOneMonth.toString());
	}

}
