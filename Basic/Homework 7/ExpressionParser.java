public class ExpressionParser {
    private static String s;
    private static int index;

    private static Expression3 operand() {
        if (s.charAt(index) == '-') {
            index++;
            return new UnaryMinus(brackets());
        }

        if (s.charAt(index) == '~') {
            index++;
            return new Not(brackets());
        }

        if (s.length() > index + 2 && s.substring(index, index + 3).equals("abs")) {
            index += 3;
            return new Abs(brackets());
        }

        if (s.length() > index + 1 && s.substring(index, index + 2).equals("lb")) {
            index += 2;
            return new Log(brackets());
        }

        Expression3 ans;

        if (Character.isDigit(s.charAt(index))) {
            int value = 0;

            while (Character.isDigit(s.charAt(index))) {
                value = value * 10 + (int) (s.charAt(index) - '0');
                index++;

                if (s.length() == index) {
                    break;
                }
            }

            ans = new Const(value);
        } else {
            String var = "";

            while (s.charAt(index) >= 'x' && s.charAt(index) <= 'z') {
                var = var + s.charAt(index);
                index++;

                if (s.length() == index) {
                    break;
                }
            }

            ans = new Variable(var);
        }

        return ans;
    }

    private static Expression3 brackets() {
        Expression3 ans;

        if (s.charAt(index) == '(') {
            index++;
            ans = expression();
            index++;
        } else {
            ans = operand();
        }

        return ans;
    }

    private static Expression3 expression() {
        Expression3 ans = prior();

        while (s.length() > index) {
            switch (s.charAt(index)) {
                case '+':
                    index++;
                    ans = new Add(ans, prior());
                    break;
                case '-':
                    index++;
                    ans = new Subtract(ans, prior());
                    break;
                default:
                    return ans;
            }
        }

        return ans;
    }

    private static Expression3 prior() {
        Expression3 ans = brackets();

        while (s.length() > index) {
            switch (s.charAt(index)) {
                case '^':
                    index++;
                    ans = new Power(ans, prior());
                    break;
                case '*':
                    index++;
                    ans = new Multiply(ans, brackets());
                    break;
                case '/':
                    index++;
                    ans = new Divide(ans, brackets());
                    break;
                default:
                    return ans;
            }
        }

        return ans;
    }

    private static void error() throws ParsingException {
        throw new ParsingException("Parsing error in " + index);
    }

    public static Expression3 parse(String t) throws ParsingException {
        s = t.replaceAll("\\s+", "");
        index = 0;
        Expression3 ans = expression();

        if (index != s.length()) {
            System.out.println(1);
            error();
        }

        return ans;
    }

/*    public static void main(String[] args) {
        System.out.println(parse(args[0]).evaluate(0, 0, 0));
    }*/
}
