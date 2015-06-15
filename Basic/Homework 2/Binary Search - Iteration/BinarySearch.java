public class BinarySearch {
    public static void main(String[] args) {

        int x = Integer.parseInt(args[0]);
        int n = args.length - 1;
        int a[] = new int[n];
        boolean found = false;
        int ans = 0;

        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(args[i + 1]);
        }

        int left = 0;
        int right = n - 1;
        int middle = (left + right) / 2;

        // Pre: left = 0 & right = n - 1 middle = (left + right) / 2 & n = a.size() & a is sorted & ans = 0
        // Post: left <= middle <= right & a is sorted & ans is the minimal index so that a[ans] >= x | ans = 0 if all elements in a < x
        // inv: 0 <= left <= middle <= right < n 
        while (!found && a[n - 1] >= x && a[0] < x) {
            middle = (left + right) / 2;

            if (a[middle] >= x && a[middle - 1] < x) {
                ans = middle;
                break;
            }

            if (a[middle] >= x) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        if (a[n - 1] < x) {
            System.out.println("Not found!");
        } else {
            System.out.println(ans + 1);
        }
    }
}
