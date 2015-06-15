public class ArrayStackTest {
    private static void fill(ArrayStack stack) {
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
    }

    private static void show(ArrayStack stack) {
        while (!stack.isEmpty()) {
            System.out.println(stack.size() + " " + stack.peek() + " " + stack.pop());
        }
    }

    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack();
        fill(stack);
        show(stack);
    }
}
