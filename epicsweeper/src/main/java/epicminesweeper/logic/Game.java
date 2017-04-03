package epicminesweeper.logic;

public class Game {

    private Board board;
    private int bombs;


    public Game(int height, int width, int bombs) {
        this.board = new Board(height, width, bombs);
        this.bombs = board.getBombs();

    }


    // constructor with a serializedBoard
    public Game(int height, int width, String serializedBoard) {
        this.board = new Board(height, width, serializedBoard);
        this.bombs = board.getBombs();

//        System.out.println(board.exportBoard());
    }

    public Game(Difficulty difficulty) {
        if (difficulty == Difficulty.EASY) {
            this.board = new Board(10, 10, 5);
        } else if (difficulty == Difficulty.INTERMEDIATE) {
            this.board = new Board(20, 20, 40);
        } else if (difficulty == Difficulty.HARD) {
            this.board = new Board(30, 30, 50);
        } else {
            throw new IllegalArgumentException("Unsupported Difficulty value passed");
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
