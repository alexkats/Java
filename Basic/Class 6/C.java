package ru.ifmo.neerc.java;

public class B extends AA {
    int x = 13;
    class A {
        int x = 10; //до этого выводилось 13, теперь 10
        void a() {
            System.err.println(x);//по факту this.x
            System.err.println(this.x);
            System.err.println(B.this.x);
            System.err.println(((AA)B.this).x);//получили ссылку на внешний класс, так можно делать, если очень нужно, но лучше не надо
            System.err.println(B.super.x);//обратится к x = 13 (скорее всего, но не факт)

        }
    }

    void run() {
        A a = new A();
        a.a();
    }

    public static void main(String[] args) {
        new B().run();
    }

    interface If {
        void run();
    }
}
