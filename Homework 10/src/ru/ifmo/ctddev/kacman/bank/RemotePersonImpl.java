package ru.ifmo.ctddev.kacman.bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;

public class RemotePersonImpl implements RemotePerson {

    private String name;
    private String surname;
    private String passportNumber;
    private ConcurrentHashMap<String, Account> accounts;

    public RemotePersonImpl(String name, String surname, String passportNumber) throws RemoteException {
        this.name = name;
        this.surname = surname;
        this.passportNumber = passportNumber;
        accounts = new ConcurrentHashMap<>();
        UnicastRemoteObject.exportObject(this, 0);
    }

    public RemotePersonImpl(Person p) throws RemoteException {
        try {
            name = p.getName();
            surname = p.getSurname();
            passportNumber = p.getPassportNumber();
            accounts = p.getAccounts();
        } catch (Exception e) {
            name = "";
            surname = "";
            passportNumber = "";
            accounts = new ConcurrentHashMap<>();
        }
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public String getSurname() throws RemoteException {
        return surname;
    }

    @Override
    public String getPassportNumber() throws RemoteException {
        return passportNumber;
    }

    @Override
    public ConcurrentHashMap<String, Account> getAccounts() throws RemoteException {
        return accounts;
    }

    @Override
    public int setAmount(String accountId, int amount) throws RemoteException {
        Account account = getAccount(accountId);
        account.setAmount(amount);
        accounts.put(accountId, account);
        return amount;
    }

    @Override
    public String showMoney(String accountId) throws RemoteException {
        Account account = getAccount(accountId);
        return ("Name: " + name + "\nSurname: " + surname + "\nPassport number: " + passportNumber + "\nID: " + account.getId() + "\nMoney: " + account.getAmount());
    }

    @Override
    public int getMoney(String accountId) throws RemoteException {
        return getAccount(accountId).getAmount();
    }

    private Account getAccount(String accountId) throws RemoteException {
        if (!accounts.containsKey(accountId)) {
            accounts.put(accountId, new AccountImpl(accountId));
        }
        return accounts.get(accountId);
    }
}