package ru.ifmo.neerc.java;

public class G {
    int x;
    public G(int i) {
        x = i;
    }

    int add(int a) {
        return x + a;
    }
}

class BB extends G {
    int add(int a) {
        return x + 2 * a;
    }
    BB(int i) {
        super(i);
        add(1);
        super.add(1);
    }

    BB() {
        this(i);
    }
}
