public class BoardConfiguration {
    public final int size = 10;
    public char cell[][] = new char[size][size];
    public boolean goodCell = false;

    public static condition() {
        System.out.print("  ");

        for (int i = 0; i < size; i++) {
            System.out.print(((char) (i + 97)) + " ");
        }

        System.out.println();
        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + " ");

            for (int j = 0; j < size; j++) {
                System.out.print(cell[i][j] + " ");
            }

            System.out.println();
            System.out.println();
        }
    }

    public void hit(int hor, int vert) {
        if (hor < 0 || hor >= size || vert < 0 || vert >= size) {
            System.out.println("You're trying to hit not existing cell! Try again!");
        if (cell[hor][vert] == 'e') {
            cell[hor][vert] = 'w';
            goodCell = true;
            System.out.println("You missed!");
        } else if (cell[hor][vert] == 's') {
            if (noShip(hor, vert)) {
                cell[hor][vert] = 'k';
                goodCell = true;
                System.out.println("You sank ship!");
            } else {
                cell[hor][vert] = 'h';
                goodCell = true;
                System.out.println("You hit ship!");
            }
        } else if (cell[hor][vert] == 'w' || cell[hor][vert] == 'k' || cell[hor][vert] == 'h') {
            System.out.println("You've already made this move! Try again!");
        }
    }
}
