public class SumSquares {
    public static Double Sum(ArrayDeque deque) {
        double ans = 0.0;
        int i = 0;

        while (i != deque.size()) {
            double x = (Double) deque.removeFirst();
            deque.addLast(x);
            ans += x * x;
            i++;
        }

        return ans;
    }
}
