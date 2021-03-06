public class Ternary extends BinaryOperation {
    public Ternary(Expression3 a, Expression3 b, Expression3 c) {
        super(a, b, c);
    }

    public double evaluate(double x, double y, double z) {
        if (first.evaluate(x, y, z) != 0) {
            return second.evaluate(x, y, z);
        } else {
            return third.evaluate(x, y, z);
        }
    }
}
