package ru.ifmo.ctddev.kacman.bank;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public interface LocalPerson extends Serializable, Person {

    String getName();
    String getSurname();
    String getPassportNumber();
    ConcurrentHashMap<String, Account> getAccounts();
    int setAmount(String accountId, int amount);
    String showMoney(String key);
    int getMoney(String key);
}
