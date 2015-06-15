public class Subtract extends BinaryOperation {
    public Subtract(Expression3 a, Expression3 b) {
        super(a, b);
    }

    public double evaluate(double x, double y, double z) {
        return first.evaluate(x, y, z) - second.evaluate(x, y, z);
    }
}
