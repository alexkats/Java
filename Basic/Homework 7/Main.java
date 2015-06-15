public class Main {
    public static void main(String[] args) {
        /*int x = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);
        int z = Integer.parseInt(args[3]);
        int res = ExpressionParser.parse(args[0]).evaluate(x, y, z);*/

        try {
            Expression3 ans = ExpressionParser.parse("1000000000000000000000");
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    try {
                        System.out.println(i + " " + j + " " + ans.evaluate(i, j, 0));
                    } catch (OverflowException e) {
                        System.out.println(i + " " + j + " " + "Overflow");
                    } catch (DivisionByZeroException e) {
                        System.out.println(i + " " + j + " " + "Division by zero");
                    } catch (LogException e) {
                        System.out.println(i + " " + j + " " + "Log exception");
                    } catch (MathException e) {
                        System.out.println(i + " " + j + " " + "Unknown exception");
                    }
                }
            }
        } catch (ParsingException e) {
        }
    }
}
