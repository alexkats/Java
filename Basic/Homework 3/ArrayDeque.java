//inv: size >= 0 && элементы deque лежат от head до tail
public class ArrayDeque {
    private int size;
    private int head;
    private int tail;
    private Object[] elements = new Object[5];

    //pre: element != null
    public void addLast(Object element) {
        assert element != null;

        ensureCapacity(++size);
        elements[tail++] = element;

        if (tail == elements.length) {
            tail = 0;
        }
    }
    //post: element добавлен в очередь последним, остальные элементы не изменены
    //post: newtail = tail + 1

    //pre: element != null
    public void addFirst(Object element) {
        assert element != null;

        ensureCapacity(++size);
        head--;

        if (head < 0) {
            head = elements.length - 1;
        }

        elements[head] = element;
    }
    //post:  element добавлен в очередь первым, остальные элементы не изменены
    //post: newhead = head - 1

    private void ensureCapacity(int capacity) {
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

    //pre: size > 0 
    public Object removeFirst() {
        assert size > 0;

        Object ans = elements[head++];
        head %= elements.length;
        size--;

        return ans;
    }
    //post: result = первый элемент, он удален из очереди
    //post: newhead = head + 1
    //post: newsize = size - 1

    //pre: size > 0
    public Object removeLast() {
        assert size > 0;

        tail--;

        if (tail < 0) {
            tail = elements.length - 1;
        }

        size--;
        return elements[tail];
    }
    //post: result = последний элемент, он удален из очереди
    //post: newtail = tail - 1
    //post: newsize = size - 1

    //pre: size > 0
    public Object peekFirst() {
        assert size > 0;

        return elements[head];
    }
    //post: result = первый элемент, он не удален из очереди

    //pre: size > 0
    public Object peekLast() {
        assert size > 0;

        if (tail < 1) {
            return elements[elements.length - 1];
        } else {
            return elements[tail - 1];
        }
    }
    //post: result = последний элемент, он не удален из очереди

    public int size() {
        return size;
    }
    //post: result == size

    public boolean isEmpty() {
        return size == 0;
    }
    //post: result <=> size == 0
}
