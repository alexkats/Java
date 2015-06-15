public class SumSquaresTest {
    private static void fill(ArrayDeque deque) {
        for (int i = 1; i <= 3; i++) {
            deque.addFirst((double) i);
        }
    }

    private static Double Sum(ArrayDeque deque) {
        return SumSquares.Sum(deque);
    }

    public static void main(String[] args) {
        ArrayDeque deque = new ArrayDeque();
        fill(deque);
        System.out.println(Sum(deque));
    }
}
