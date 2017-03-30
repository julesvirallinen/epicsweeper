package epicminesweeper.logic;


public class Game {

    private Board board;
    private int bombs;


    public Game(int height, int width, int bombs) {
        this.board = new Board(height, width, bombs);
        this.bombs = board.getBombs();

    }


    // constructor with a seed
    public Game(int height, int width, String seed) {
        this.board = new Board(height, width, seed);
        this.bombs = board.getBombs();

//        System.out.println(board.exportBoard());
    }

    public Game(int difficulty) {
        if (difficulty == 1) {
            this.board = new Board(10, 10, 5);
        } else if (difficulty == 2) {
            this.board = new Board(20, 20, 30);
        } else {
            this.board = new Board(30, 30, 50);
        }
        this.bombs = board.getBombs();

    }

    public boolean clickTile(int x, int y) {
        return board.clickTile(x, y);
    }

    public Board getBoard() {
        return board;
    }

    public void flagTile(int x, int y) {
        board.flagTile(x, y);
    }

    public boolean gameWon() {
        return board.hasGameBeenWon();
    }
}
