package com.example.application;

import com.example.bank.api.Bank;
import com.example.dto.BankCard;
import com.example.dto.BankCardType;
import com.example.dto.User;
import com.example.service.api.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

	private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(Util.class.getName());

	private static Bank bank;

	private static Service service;

	private static List<BankCard> bankCards = new ArrayList<>();

	public static void initServices() {
		var bankServiceLoader = ServiceLoader.load(Bank.class);
		log.info("Bank is present: " + bankServiceLoader.findFirst().isPresent());
		bank = bankServiceLoader.findFirst().orElseThrow();

		var serviceServiceLoader = ServiceLoader.load(Service.class);
		log.info("Service is present: " + serviceServiceLoader.findFirst().isPresent());
		service = serviceServiceLoader.findFirst().orElseThrow();
	}

	public static void generateData() {
		generateUserData();
		generateCards();
	}

	public static void generateUserData() {
		service.addUser(createUser("Tyrell", "Coleman", LocalDate.of(1977, 6, 19)));
		service.addUser(createUser("Brian", "Brown", LocalDate.of(1996, 10, 13)));
		service.addUser(createUser("Veronica", "Gentry", LocalDate.of(1948, 9, 4)));
		service.addUser(createUser("Robert", "Mitchell", LocalDate.of(2005, 3, 24)));
	}

	public static User createUser(String name, String surname, LocalDate birthday) {
		User user = new User();
		user.setName(name);
		user.setSurname(surname);
		user.setBirthday(birthday);
		return user;
	}

	public static void generateCards() {
		service.getAllUsers().stream()
				.map(user -> bank.createBankCard(user, getRandomCardType()))
				.forEach(Util::saveAndSubscribeBankCard);
	}

	public static void saveAndSubscribeBankCard(BankCard card) {
		bankCards.add(card);
		service.subscribe(card);
	}

	public static BankCardType getRandomCardType() {
		var randomInt = ThreadLocalRandom.current().nextInt(1);
		return BankCardType.values()[randomInt];
	}

	public static Service getService() {
		return service;
	}

	public static List<BankCard> getBankCards() {
		return bankCards;
	}

}
