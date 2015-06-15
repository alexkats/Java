public class LinkedStackTest {
    private static void fill(LinkedStack stack) {
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
    }

    private static void show(LinkedStack stack) {
        while (!stack.isEmpty()) {
            System.out.println(stack.size() + " " + stack.peek() + " " + stack.pop());
        }
    }

    public static void main(String[] args) {
        LinkedStack stack = new LinkedStack();
        fill(stack);
        show(stack);
    }
}
