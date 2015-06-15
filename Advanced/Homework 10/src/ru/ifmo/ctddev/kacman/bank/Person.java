package ru.ifmo.ctddev.kacman.bank;

import java.util.concurrent.ConcurrentHashMap;

public interface Person {

    String getName() throws Exception;
    String getSurname() throws Exception;
    String getPassportNumber() throws Exception;
    ConcurrentHashMap<String, Account> getAccounts() throws Exception;
    int setAmount(String accountId, int amount) throws Exception;
    String showMoney(String key) throws Exception;
    int getMoney(String key) throws Exception;
}
