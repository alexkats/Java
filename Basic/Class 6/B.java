/*import ru.ifmo.neerc.java.AA;
import ch.melnikov.test.*;
import static ru.ifmo.neerc.java.AA.PI;*/

package ru.ifmo.neerc.java;

public class B extends AA /*не было сначала*/{

    int x;
    
    class A { 
        void a() {

        }
    }

    // У нас есть класс B, у него есть вложенный класс A

    interface If {
        void run();
    }
    ch.melnikov.test.AA a;
    ru.ifmo.neerc.java.AA a2;
    AA b;

    /*void someVoid() {
        new If() {
            public void run() {

            }
        }.run();
        new Thread(new Runnable() { //Анонимные классы // Не создается класс для реализации интерфейса, сразу пишется нужная реализация интерфейса
            @Override
            public void run() {
            }
        });
    }*/

    void someVoid() {
        final int x = 0;
        new If() {
            public void run() {
                System.err.println(x);
            }
        }.run();
    }
    
    /*void run() {
        b.x1; только так
    }*/

    /*void run() {
        System.err.println(x3); // extends
        B c = null;
        System.err.println(c.x3); //good
        System.err.println(b.x3); //bad
    }*/

    public static void main(String[] args) {
        System.err.println(AA.PI);
    }
}
