import java.util.*;

public class BinarySearch {

    public static ArrayList<Integer> a = new ArrayList<Integer>();

    //inv: left <= right && x != null && (a[left - 1] <= x && a[right] >= x)
    //pre: i <= j => a[i] <= a[j]
    public static int find_left(int left, int right, int x) {
        if (left > right) {
            return 0;
        }

        if (a.get(0) >= x) {
            return 0;
        }

        if (a.get(a.size() - 1) < x) {
            return a.size();
        }

        int middle = (left + right) / 2;

        if (a.get(middle) >= x && a.get(middle - 1) < x) {
            return middle;
        }

        if (a.get(middle) >= x) {
            return find_left(left, middle - 1, x);
        } else {
            return find_left(middle + 1, right, x);
        }
    }
    //post: result == индекс первого элемента, равного x || result == индекс первого элемента

    //pre: i <= j => a[i] <= a[j]
    public static int find_right(int x) {
        int left = 0;
        int right = a.size() - 1;
        int middle = (left + right) / 2;
        boolean found = false;

        if (left > right) {
            return -1;
        }

        if (a.get(a.size() - 1) < x) {
            return a.size() - 1;
        }

        if (a.get(0) > x) {
            return -1;
        }

        //inv: left <= right && (a[left - 1] <= x && a[right] >= x)
        while (!found) {
            middle = (left + right) / 2;

            if (a.get(middle) > x && a.get(middle - 1) < x) {
                return middle - 1;
            }

            if (a.get(middle) == x && middle == a.size() - 1) {
                return middle;
            }

            if (a.get(middle) == x && a.get(middle + 1) > x) {
                return middle;
            }

            if (a.get(middle) > x) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        return 0;
    }
    //post: result == индекс первого элемента, равного x || result == индекс первого элемента

    public static void main(String[] args) {

        a.clear();
        int x = Integer.parseInt(args[0]);
        int n = args.length - 1;

        for (int i = 0; i < n; i++) {
            a.add(Integer.parseInt(args[i + 1]));
        }

        int left = find_left(0, n - 1, x);
        int right = find_right(x);

        System.out.println(left + " " + (right - left + 1));

    }
    //post: i <= j => a[i] <= a[j] && result1 - самый левый индекс x || куда его нужно вставить, если его нет && result2 - количество элементов x
}
