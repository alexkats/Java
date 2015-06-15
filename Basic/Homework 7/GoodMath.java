public class GoodMath {
    public static int add(int first, int second) throws OverflowException {
        long ans = first + second;
        return longToInt(ans);
    }

    public static int subtract(int first, int second) throws OverflowException {
        long ans = first - second;
        return longToInt(ans);
    }

    public static int multiply(int first, int second) throws OverflowException {
        long ans = first * second;
        return longToInt(ans);
    }

    public static int divide(int first, int second) throws DivisionByZeroException {
        if (second != 0) {
            return first / second;
        } else {
            throw new DivisionByZeroException();
        }
    }

    public static int unaryMinus(int first) throws OverflowException {
       if (first != Integer.MIN_VALUE) {
            return -first;
        } else {
            throw new OverflowException();
        }
    }

    public static int abs(int first) throws OverflowException {
        if (first != Integer.MIN_VALUE) {
            return Math.abs(first);
        } else {
            throw new OverflowException();
        }
    }

    public static int log(int first) throws LogException {
        if (first > 0) {
            return (int) (Math.log((double) first) / Math.log(2.0));
        } else {
            throw new LogException();
        }
    }

    public static int pow(int first, int second) throws OverflowException {
        double ans = Math.pow(first, second);
        return doubleToInt(ans);
    }

    public static int longToInt(long a) throws OverflowException {
        if (a > Integer.MAX_VALUE || a < Integer.MIN_VALUE) {
            throw new OverflowException();
        }
     
        return (int) a;
    }

    public static int doubleToInt(double a) throws OverflowException {
        if (a > Integer.MAX_VALUE || a < Integer.MIN_VALUE) {
            throw new OverflowException();
        }

        return (int) a;
    }
}
