public class Subtract extends Operation {
    public Subtract(Expression3 first, Expression3 second) {
        super(first, second);
    }

    public int evaluate(int x, int y, int z) throws MathException {
        return GoodMath.subtract(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
