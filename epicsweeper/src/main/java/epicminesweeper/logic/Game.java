package epicminesweeper.logic;

/**
 * Manages a game. Has stats on clicks and timer, and has a board for the actual game.
 * @author Julius uusinarkaus
 */
public class Game {

    private Board board;
    private int bombs;

    /**
     * Initializes board of certain size and bomb count.
     * @param height
     * @param width
     * @param bombs
     */
    public Game(int height, int width, int bombs) {
        this.board = new Board(height, width, bombs);
        this.bombs = board.getBombs();

    }


    // constructor with a serializedBoard

    /**
     *
     * Creates game with board that from serial, mainly for testing, or challenges.
     * @param height
     * @param width
     * @param serializedBoard
     */
    public Game(int height, int width, String serializedBoard) {
        this.board = new Board(height, width, serializedBoard);
        this.bombs = board.getBombs();

//        System.out.println(board.exportBoard());
    }

    /**
     * Initializes board or difficulty. Probably main constructor of class.
     * @param difficulty
     */
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

    /**
     * Used to click on tile by coordinate. Maybe deprecated if clicking happens straight to node.
     * @param x
     * @param y
     * @return
     */
    public boolean clickTile(int x, int y) {
        return board.clickTile(x, y);
    }

    /**
     *
     * @return
     */
    public Board getBoard() {
        return board;
    }

    /**
     *
     * Flags tile by coordinate. Maybe deprecated if flagging straight to board.
     * @param x
     * @param y
     */
    public void flagTile(int x, int y) {
        board.flagTile(x, y);
    }

    /**
     * Returns true if boards winstate is true.
     * @return
     */
    public boolean gameWon() {
        return board.hasGameBeenWon();
    }
}
