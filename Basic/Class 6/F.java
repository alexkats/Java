package ru.ifmo.neerc.java;

final public class C /*extends Integer - нельзя*/{ //не может наследоваться
    public C(int i) {
        super(i);
    }

    final void r() { //нельзя переопределять в наследниках
    }
}
