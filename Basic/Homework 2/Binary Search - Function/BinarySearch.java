import java.util.*;

public class BinarySearch {

    public static ArrayList<Integer> a = new ArrayList<Integer>(); 

    // Pre: a is sorted & 0 <= left <= right < a.size()
    // Post: a is sorted & 0 <= left <= right < a.size() & -1 <= result < a.size() & result is minimum index so that a[i] >= x | result = -1 if all elements in a < x
    public static int bin_search(int left, int right, int x) {
        int middle = (left + right) / 2;

        if (a.get(a.size() - 1) < x) {
            return -1;
        }

        if (a.get(0) >= x) {
            return 0;
        }

        if (a.get(middle) >= x && a.get(middle - 1) < x) {
            return middle;
        }

        if (a.get(middle) >= x) {
            return bin_search(left, middle - 1, x);
        } else {
            return bin_search(middle + 1, right, x);
        }
    } 

    public static void main(String[] args) {

        int n = args.length - 1;
        int x = Integer.parseInt(args[0]);

        for (int i = 0; i < (args.length - 1); i++) {
            a.add(Integer.parseInt(args[i + 1]));
        }

        int ans = bin_search(0, n - 1, x);

        if (ans == -1) {
            System.out.println("Not found!");
        } else {
            System.out.println(ans + 1);
        }

    }
}
