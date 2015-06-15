public class ArrayDequeSingleton {
    private static int size = 0;
    private static int head = 0;
    private static int tail = 0;
    private static Object[] elements = new Object[5];

    public static void addLast(Object element) {
        assert element != null;

        ensureCapacity(++size);
        elements[tail++] = element;

        if (tail == elements.length) {
            tail = 0;
        }
    }

    public static void addFirst(Object element) {
        assert element != null;

        ensureCapacity(++size);
        head--;

        if (head < 0) {
            head = elements.length - 1;
        }

        elements[head] = element;
    }

    private static void ensureCapacity(int capacity) {
        int q = elements.length;
        
        if (q < capacity) {
            Object[] newElements = new Object[capacity * 2];

            for (int i = 0; i < q; i++) {
                newElements[i] = elements[(i + head) % q];
            }

            elements = newElements;
            head = 0;
            tail = size - 1;
        }
    }

    public static Object removeFirst() {
        assert size > 0;

        Object ans = elements[head++];
        head %= elements.length;
        size--;

        return ans;
    }

    public static Object removeLast() {
        assert size > 0;

        tail--;

        if (tail < 0) {
            tail = elements.length - 1;
        }

        size--;
        return elements[tail];
    }

    public static Object peekFirst() {
        assert size > 0;

        return elements[head];
    }

    public static Object peekLast() {
        assert size > 0;

        if (tail < 1) {
            return elements[elements.length - 1];
        } else {
            return elements[tail - 1];
        }
    }

    public static int size() {
        return size;
    }

    public static boolean isEmpty() {
        return size == 0;
    }
}
