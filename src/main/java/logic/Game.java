package logic;


public class Game {

    private Board board;

    public Game(int height, int width, int bombs) {
        this.board = new Board(height, width, bombs);
        board.init();
        board.printBoard();

    }

    public Game(int height, int width, String seed) {
        this.board = new Board(height, width, seed);
        board.printBoard();
        System.out.println(board.exportBoard());
    }

}
