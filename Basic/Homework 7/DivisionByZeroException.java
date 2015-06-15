public class DivisionByZeroException extends MathException {
    public DivisionByZeroException() {
        super("");
    }

    public DivisionByZeroException(String msg) {
        super(msg);
    }
}
