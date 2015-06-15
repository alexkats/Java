public class Board {
    public int size = 10;
    public char[][] cell = new char[size][size];

    public void build() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cell[i][j] = 'e'; //empty
            }
        }
    }
}
