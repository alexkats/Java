public class Log extends Operation {
    public Log(Expression3 first) {
        super(first);
    }

    public int evaluate(int x, int y, int z) throws MathException {
        int ans = GoodMath.log(first.evaluate(x, y, z));
        return ans;
    }
}
