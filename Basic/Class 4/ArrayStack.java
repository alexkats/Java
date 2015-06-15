public class ArrayStack extends AbstractStack /*implements Stack можно писать, но уже бессмысленно*/{
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
}
