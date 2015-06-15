public class UnaryMinus extends Operation {
    public UnaryMinus(Expression3 first) {
        super(first);
    }

    public int evaluate(int x, int y, int z) {
        return -first.evaluate(x, y, z);
    }
}
