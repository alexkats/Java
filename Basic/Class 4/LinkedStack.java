public class LinkedStack extends AbstractStack /*implements Stack*/ {
    private int size;
    private Node head;

    public void push(Object element) {
        head = new Node(element, head);
        size++;
    }

    //@Override
/*    public int size() {
        //переопределение size
        //super.size(); - взять предка
    }
*/
    protected Object popImpl() {
        Object result = head.value;
        head = head.next;
        return result;
    }

    private static class Node {
        private final Object value;
        private final Node next;

        public Node() {
            value = null;
            next = null;
        }

        public Node(Object v, Node n) {
            value = v;
            next = n;
            /*LinkedStack.this.*/ //head = null; после static
        }
    }
    //final - нечто, определенное только один раз
}
