public class A {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);
        int z = Integer.parseInt(args[3]);

        try {
            int ans = ExpressionParser.parse(args[0]).evaluate(x, y, z);
            System.out.println("ans = " + ans);
        } catch (ParsingException e) {
        }
    }
}
