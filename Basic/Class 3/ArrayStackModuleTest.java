public class ArrayStackModuleTest {
    private static void fill() {
        for (int i = 0; i < 10; i++) {
            ArrayStackModule.push(i);
        }
    }

    private static void show() {
        while (!ArrayStackModule.isEmpty()) {
            System.out.println(ArrayStackModule.size() + " " + ArrayStackModule.peek() + " " + ArrayStackModule.pop());
        }
    }

    public static void main(String[] args) {
        fill();
        show();
    }
}
