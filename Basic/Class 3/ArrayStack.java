public class ArrayStack implements Stack{
    private int size;
    private Object[] elements = new Object[5];

    public void push(Object element) {
        assert element != null;

        ensureCapacity(size + 1);
        elements[size++] = element;
    }

    private void ensureCapacity(int capacity) {
        if (capacity <= elements.length) {
            return;
        }

        Object[] newElements = new Object[capacity * 2];

        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements;
    }

    public Object pop() {
        assert size > 0;

        return elements[--size];
    }

    public Object peek() {
        assert size > 0;

        return elements[size - 1];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
