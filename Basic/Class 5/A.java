public class A {
    public static void main(String[] args) {
        byte a = 0; // -128 127
        short b = 1; // -2^15..2^15-1
        int c = 2;
        long d = 30_000_000____000_000L;
        long e = 0_____0;
        long f = 0xab______ba;
        int g = 0123123; // ведущий 0 - восьмиричное число
        int h = 0b010010101010100;
        long i = 0xab___baDEAD;
        Byte aa = a;
        Short bb = b;
        Integer cc = c;
        Long dd = d;
        // Byte.MAX_VALUE
        Long x1 = 1231892369817236L;
        Long x2 = x1 - 1; // Long.valueOf(x1.longValue() - 1); формально
        long x3 = x2 + 1;
        // x1 = null;
        System.err.println(x1 == x3);
    }
}
