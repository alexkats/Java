public class HumanPlayer implements Player {
    public int hor;
    public int vert;

    public void movement(int hor, int vert) {
        this.hor = hor;
        this.vert = vert;
    }

    public void makeMove(BoardConfiguration configuration) {
        configuration.hit(hor, vert);
    }
}
