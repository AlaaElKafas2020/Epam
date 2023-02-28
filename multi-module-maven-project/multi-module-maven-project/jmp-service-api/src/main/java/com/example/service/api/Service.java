package com.example.service.api;

import com.example.dto.BankCard;
import com.example.dto.Subscription;
import com.example.dto.User;
import com.example.service.api.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Predicate;

public interface Service {


    void subscribe(BankCard card);

    Subscription getSubscriptionByBankCardNumber(String cardNumber) throws EntityNotFoundException;

    List<Subscription> getAllSubscriptions();

    List<User> getAllUsers();

    void addUser(User user);

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition);

    default double getAverageUserAge() {
        return getAllUsers().stream()
                .mapToLong(Service::getAge)
                .average()
                .orElse(0);
    }

    static boolean isPayableUser(User user) {
        return Double.compare(getAge(user), 18) >= 0;
    }

    private static long getAge(User user) {
        return ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now());
    }




}
