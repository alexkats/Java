public class ArrayDequeADT {
    private int size = 0;
    private int head = 0;
    private int tail = 0;
    private Object[] elements = new Object[5];

    public static void addLast(ArrayDequeADT deque, Object element) {
        assert element != null;

        ensureCapacity(deque, ++deque.size);
        deque.elements[deque.tail++] = element;

        if (deque.tail == deque.elements.length) {
            deque.tail = 0;
        }
    }

    public static void addFirst(ArrayDequeADT deque, Object element) {
        assert element != null;

        ensureCapacity(deque, ++deque.size);
        deque.head--;

        if (deque.head < 0) {
            deque.head = deque.elements.length - 1;
        }

        deque.elements[deque.head] = element;
    }

    private static void ensureCapacity(ArrayDequeADT deque, int capacity) {
        int q = deque.elements.length;

        if (q < capacity) {
            Object[] newElements = new Object[capacity * 2];

            for (int i = 0; i < q; i++) {
                newElements[i] = deque.elements[(i + deque.head) % q];
            }

            deque.elements = newElements;
            deque.head = 0;
            deque.tail = deque.size - 1;
        }
    }

    public static Object removeFirst(ArrayDequeADT deque) {
        assert deque.size > 0;

        Object ans = deque.elements[deque.head++];
        deque.head %= deque.elements.length;
        deque.size--;

        return ans;
    }

    public static Object removeLast(ArrayDequeADT deque) {
        assert deque.size > 0;

        deque.tail--;

        if (deque.tail < 0) {
            deque.tail = deque.elements.length - 1;
        }

        deque.size--;
        return deque.elements[deque.tail];
    }

    public static Object peekFirst(ArrayDequeADT deque) {
        assert deque.size > 0;

        return deque.elements[deque.head];
    }

    public static Object peekLast(ArrayDequeADT deque) {
        assert deque.size > 0;

        if (deque.tail < 1) {
            return deque.elements[deque.elements.length - 1];
        } else {
            return deque.elements[deque.tail - 1];
        }
    }

    public static int size(ArrayDequeADT deque) {
        return deque.size;
    }

    public static boolean isEmpty(ArrayDequeADT deque) {
        return deque.size == 0;
    }
}
