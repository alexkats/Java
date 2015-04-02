import java.io.*;
import java.util.*;

public class A {
    BufferedReader in;
    PrintWriter out;

    public void run() {
        try {
            in = new BufferedReader(new FileReader(new File("err")));
            out = new PrintWriter(new File("result"));
            ArrayList <String> t = new ArrayList <String> ();

            while (true) {
                String s = in.readLine();

                if (s == null) {
                    break;
                }

                if (s.substring(0, 4).equals("Test")) {
                    t.add(s);
                }
            }

            Collections.sort(t);

            if (t.size() == 0) {
                in.close();
                in = new BufferedReader(new FileReader(new File("aaa")));

                while (true) {
                    String s = in.readLine();

                    if (s == null) {
                        break;
                    }

                    t.add(s);
                }
            } else {
                String s = String.format("%d tests out of 15 passed", (15 - t.size()));
                t.add("");
                t.add(s);
            }

            for (int i = 0; i < t.size(); i++) {
                out.println(t.get(i));
            }

            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new A().run();
    }
}
