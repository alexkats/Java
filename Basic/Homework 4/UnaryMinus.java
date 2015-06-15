public class UnaryMinus extends BinaryOperation {
    public UnaryMinus(Expression3 a) {
        super(a);
    }

    public double evaluate(double x, double y, double z) {
        return -first.evaluate(x, y, z);
    }
}
