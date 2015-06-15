public abstract class AbstractStack implements Stack {
    protected int size;
    
    public Object peek() {
        assert size > 0;

        Object result = pop();
        push(result); 
        return result;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Object pop() {
        assert size > 0;

        Object value = popImpl();
        size--;
        return value;
    }

    protected abstract Object popImpl();
}
