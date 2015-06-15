public class Variable implements Expression3 {
    private final String var;

    public Variable(String var) {
        assert var != null;

        this.var = var;
    }

    public int evaluate(int x, int y, int z) {
        if (var.equals("x")) {
            return x;
        } else if (var.equals("y")) {
            return y;
        } else if (var.equals("z")) {
            return z;
        }

        assert false;
        return 0;
    }
}
