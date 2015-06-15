public class UnaryMinus extends Operation {
    public UnaryMinus(Expression3 first) {
        super(first);
    }

    public int evaluate(int x, int y, int z) throws MathException {
        return GoodMath.unaryMinus(first.evaluate(x, y, z));
    }
}
