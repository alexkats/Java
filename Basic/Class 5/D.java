public class D {
    public static void main(String[] args) {
        int a = 0;
        int b = 3;
        // b = a + b * a - b / a % b;
        System.err.println(a++);
        System.err.println(++a);
        System.err.println(a);
        a = 0;
        a = a++ + ++a; // операции вызываются поп порядку слева направо
        System.err.println(a);
        a = 10;
        a += 10;
        a %= 20;
        System.err.println(a);
        long c = 123097192837192L;
        a += c;
        // a = a + c; - не компилится
        System.err.println(a);
        a = 3;
        System.err.println(a << 10);
        System.err.println(a << 40);
        System.err.println(a >> 10);
        a = -3;
        System.err.println(a >> 10); // знаковый бит расширяется - арифметический сдвиг
        System.err.println(a >>> 10); // старший бит дополняется нулями - логический сдвиг
        a = 3;
        System.err.println(a << 40);
        System.err.println(a << 8);
        a = 3;
        a = a & b | a ^ ~b;
        boolean e = true;
        e = e && e || !e; // двойная операция: первая очевидна, вторая не вычисляется
        System.err.println(a);
        System.err.println(e);
        e = e != e; // xor
        System.err.println(e);
        a >>>= a; // самый длинный оператор java
        System.err.println(Math.PI % Math.E);
        System.err.println(-Math.PI % Math.E);
        Integer aaa = 3;
        System.err.println(aaa instanceof Integer);
        System.err.println(aaa instanceof Number);
        System.err.println(e ? a : b);
    }
}
