import java.util.Arrays;

public class E {
    public static void main(String... args) {
        int[] a = {1, 2, 3};
        if (args.length == 3) {
            return;
        }
        System.err.println(a);
        System.err.println(new int[] {1, 2, 3});
        System.err.println(Arrays.toString(a));
        System.err.println(args.length);
        main("asd", "qwe", "zxc");
        String t = "asdasda";
        String s = "asdasda" + "asdasdas";
        System.err.println(t);
        System.err.println(s);
        System.err.println(s.toCharArray());
        System.err.println(s instanceof String);
        ;
        {
            final int n = 0; // final - переменную нельзя изменить
            final int nn;
            nn = 0;
        }
        {
            double n = 0;
            System.err.println("");
            System.err.println("");
        }
        // goto; calls compilation error
        // const; calls compilation error
        fori: for (int i = 0; i < 030; i++) {
            for (int j = 0; j < 0b10101 + 0xab; j++) {
                break fori;
            }
        }

        fox: {
            abc : {
                break fox;
            }
            // System.err.println("asdasd"); unreachable
        }

        if (false) {
            System.err.println("asdasd");
        }

        /*while (false) {
            System.err.println("asdasd");
        }*/

        int aa = 0;
        switch (aa) {
            case 0:
                System.err.println("0");
                break;
            case 1:
                System.err.println("1");
                break;
            case 2:
                System.err.println("2");
                break;
            default:
                System.err.println("def");
        }

        while (1 > a[0]) {

        }
        do {
            System.err.println("asd");
        } while (a[0] < 0);

        // break можно ставить в любом коде с меткой
    }
}
