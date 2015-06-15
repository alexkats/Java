public class Multiply extends Operation {
    public Multiply(Expression3 first, Expression3 second) {
        super(first, second);
    }

    public int evaluate(int x, int y, int z) {
        return first.evaluate(x, y, z) * second.evaluate(x, y, z);
    }
}
