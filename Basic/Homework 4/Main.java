public class Main {
    public static void main(String[] args) {        
        double var = Double.parseDouble(args[0]);
        double ans = new Ternary(new UnaryMinus(new Add(new Subtract(new Multiply(new Variable("x"), new Variable("y")), new Multiply(new Const(2), new Variable("z"))), new Const(1))), new Const(2), new Const(3)).evaluate(var, var, var);
        System.out.println(ans);
    }
}
