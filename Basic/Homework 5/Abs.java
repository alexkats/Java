public class Abs extends Operation {
    public Abs(Expression3 first) {
        super(first);
    }

    public int evaluate(int x, int y, int z) {
        int ans = first.evaluate(x, y, z);

        if (ans < 0) {
            ans = -ans;
        }

        return ans;
    }
}
