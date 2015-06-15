public class Power extends Operation {
    public Power(Expression3 first, Expression3 second) {
        super(first, second);
    }

    public int evaluate(int x, int y, int z) throws MathException {
        return GoodMath.pow(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
