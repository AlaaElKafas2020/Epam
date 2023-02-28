package com.example.dto;

import java.time.LocalDate;

public class Subscription {
	private String bankCard;

	private LocalDate startDate;

	public Subscription(String bankCard, LocalDate startDate) {
		this.bankCard = bankCard;
		this.startDate = startDate;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "Subscription{" +
				"bankCard='" + bankCard + '\'' +
				", startDate=" + startDate +
				'}';
	}

}
