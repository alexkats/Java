package ru.ifmo.ctddev.kacman.bank;

import java.io.Serializable;

public interface Account extends Serializable {

    String getId();
    int getAmount();
    void setAmount(int amount);
}
