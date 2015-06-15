public class Variable implements Expression3 {
    private final String var;

    public Variable(String s) {
        assert s != null;

        var = s;
    }

    public double evaluate(double x, double y, double z) {
        if (var == "x") {
            return x;
        } else if (var == "y") {
            return y;
        } else if (var == "z") {
            return z;
        }

        assert false;
        return 0.0;
    }
}
