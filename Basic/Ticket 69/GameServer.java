public class GameServer {
    private String mode;

    public void getMove(HumanPlayer human, int hor, int vert) {
        human.movement(hor, vert);
    }

    public void gameHumanHuman(HumanPlayer human1, HumanPlayer human2) {
        human1.movement(

    static BufferedReader br;
    static StringTokenizer st;
    static PrintWriter out;

    static boolean hasNextToken() {
        while (st == null || !st.hasMoreTokens()) {
            String line = br.readLine();

            if (line == null) {
                return false;
            }

            st = new StringTokenizer(line);
        }

        return true;
    }

    static String nextToken() {
        if (hasNextToken()) {
            return st.nextToken();
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        InputStream input = System.in;
        PrintStream output = System.out;
        br = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output);
        out.println("Welcome to Sea Battle!");
        out.println("You can choose 3 modes of game:");
        out.println("1) Human vs Human");
        out.println("2) Human vs Computer");
        out.println("3) Computer vs Computer");

    static int nextInt() {
        return Integer.parseInt(nextToken());
    }

    static long nextLong() {
        return Long.parseLong(nextToken());
    }

    static double nextDouble() {
        return Double.parseDouble(nextToken());
    }
}
