package logic;


public class Game {

    private Board board;

    public Game(int height, int width, int bombs) {
        this.board = new Board(height, width, bombs);
        board.init();
        board.printBoard();
    }

}
