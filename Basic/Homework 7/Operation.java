public abstract class Operation implements Expression3 {
    protected Expression3 first, second, third;

    protected Operation(Expression3 first) {
        assert first != null;

        this.first = first;
    }

    protected Operation(Expression3 first, Expression3 second) {
        assert first != null;
        assert second != null;

        this.first = first;
        this.second = second;
    }

    protected Operation(Expression3 first, Expression3 second, Expression3 third) {
        assert first != null;
        assert second != null;
        assert third != null;

        this.first = first;
        this.second = second;
        this.third = third;
    }
}
