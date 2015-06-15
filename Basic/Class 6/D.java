package ru.ifmo.neerc.java;

public class D extends AA {
    
    static class C { //не зависит от экземпляра предка, если писать static
        int y;
        static int z;
        void f() {
            System.err.println(y);
        }
    }

    ru.ifmo.neerc.java.B.c c = new C();
    int x;

    void r() {
        B b;
        B.C.z = 10; //b.C.y нельзя, b.C.z - вроде тоже
    }

    public B(int x) {
        this.x = x;
    }

    class A {
        //static int zz; нельзя, потому что класс A не статический
        void f() {
            System.err.println(B.this.x);
        }
    }

    void run() {
        A a = new A(); //выводит 1
        a.f(); //выводит 1
        B b = new B(3);
        (b.new A()).f(); //выводит 3
    }

    public static void main(String[] args) {

    }
}
