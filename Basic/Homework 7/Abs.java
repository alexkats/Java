public class Abs extends Operation {
    public Abs(Expression3 first) {
        super(first);
    }

    public int evaluate(int x, int y, int z) throws MathException {
        int ans = GoodMath.abs(first.evaluate(x, y, z));
        return ans;
    }
}
