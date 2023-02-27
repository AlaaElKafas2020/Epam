package com.example.cloud.bank.impl;

import com.example.bank.api.Bank;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import com.example.dto.BankCard;
import com.example.dto.BankCardType;
import com.example.dto.CreditBankCard;
import com.example.dto.DebitBankCard;
import com.example.dto.User;


public class CloudBankImpl implements Bank {

    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {

        BankCard card = getBankCardSupplier(cardType).get();
        card.setNumber(generateCardNumber());
        card.setUser(user);
        return card;
    }

    private Supplier<BankCard> getBankCardSupplier(BankCardType cardType) {
        Supplier<BankCard> supplier;
        if (BankCardType.DEBIT.equals(cardType)) {
            supplier = (DebitBankCard::new);
        } else if (BankCardType.CREDIT.equals(cardType)){
            supplier = (CreditBankCard::new);
        } else {
            throw new IllegalArgumentException("It is invalid type ");
        }
        return supplier;
    }

    private String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(ThreadLocalRandom.current().nextInt(10));
        }
        return cardNumber.toString();
    }
}
