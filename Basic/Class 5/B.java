public class B {
    public static void main(String[] args) {
        float f = 0.123f; // 4 bytes
        double d = 0.1231221e100; // 8 bytes
        double d1 = 010.123_123;
        double d2 = 010;
        System.err.println(d1);
        System.err.println(d2);
        Float ff = f;
        Double dd = d;
        System.err.println(Double.MAX_VALUE);
        System.err.println(Double.MIN_VALUE);
        System.err.println(-Double.MAX_VALUE*2/2);
        System.err.println(-Double.MAX_VALUE/2*2);
        System.err.println(Double.NEGATIVE_INFINITY + Double.POSITIVE_INFINITY);
        System.err.println(Double.NaN < Double.NaN);
        System.err.println(Double.NaN > Double.NaN);
        System.err.println(Double.NaN == Double.NaN);
        System.err.println(Double.NaN != Double.NaN);
        System.err.println(Math.PI);
        System.err.println(Math.E);
    }
}
