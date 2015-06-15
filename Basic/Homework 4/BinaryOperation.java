public abstract class BinaryOperation implements Expression3 {
    protected Expression3 first, second, third;

    protected BinaryOperation(Expression3 a) {
        assert a != null;

        first = a;
    }

    protected BinaryOperation(Expression3 a, Expression3 b) {
        assert a != null;
        assert b != null;

        first = a;
        second = b;
    }

    protected BinaryOperation(Expression3 a, Expression3 b, Expression3 c) {
        assert a != null;
        assert b != null;
        assert c != null;

        first = a;
        second = b;
        third = c;
    }
}
