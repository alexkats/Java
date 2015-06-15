public class Not extends Operation {
    public Not(Expression3 first) {
        super(first);
    }

    public int evaluate(int x, int y, int z) throws MathException {
        return ~first.evaluate(x, y, z);
    }
}
