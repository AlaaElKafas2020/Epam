package com.example.service.api.exception;

public class EntityNotFoundException extends RuntimeException {

	//21. Create Exception class which will be used into orElseThrow for Optional from getSubscriptionByBankCardNumber()

	public EntityNotFoundException(String message) {

		super(message);
	}

}
