public class Ternary extends Operation {
    public Ternary(Expression3 first, Expression3 second, Expression3 third) {
        super(first, second, third);
    }

    public int evaluate(int x, int y, int z) {
        if (first.evaluate(x, y, z) != 0) {
            return second.evaluate(x, y, z);
        } else {
            return third.evaluate(x, y, z);
        }
    }
}
