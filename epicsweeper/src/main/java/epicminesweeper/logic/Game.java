package epicminesweeper.logic;

/**
 * Manages a game. Has stats on clicks and timer, and has a board for the actual game.
 *
 * @author Julius Uusinarkaus
 */
public class Game {

    private Board board;
    private GameStats stats;

    /**
     * Initializes board of certain size and bomb count.
     *
     * @param height Height of board to be created
     * @param width  Width of board to be created
     * @param bombs  Amount of bombs on board
     */
    public Game(int height, int width, int bombs) {
        this.board = new Board(height, width, bombs);

        startGame();
    }

    /**
     * Creates game with board that from serial, mainly for testing, or challenges.
     *
     * @param height          height of serialized board
     * @param width           width of serialized board
     * @param serializedBoard "seed" for board, eg "ooxoooooo", for board of 3x3 with bomb in top corner.
     */
    public Game(int height, int width, String serializedBoard) {
        this.board = new Board(height, width, serializedBoard);
        startGame();
    }

    /**
     * Initializes board or difficulty. Probably main constructor of class.
     *
     * @param difficulty Difficulty of board, from enum class Difficulty
     */
    public Game(Difficulty difficulty) {
        if (difficulty == Difficulty.EASY) {
            this.board = new Board(10, 10, 5);
        } else if (difficulty == Difficulty.INTERMEDIATE) {
            this.board = new Board(20, 20, 40);
        } else if (difficulty == Difficulty.HARD) {
            this.board = new Board(30, 30, 200);
        } else {
            throw new IllegalArgumentException("Unsupported Difficulty value passed");
        }
        startGame();


    }

    public void startGame() {
        this.stats = new GameStats(board.getBombs());
    }


    /**
     * Used to click on tile by node.
     *
     * @param n Node to be clicked.
     * @return Returns false if game is lost because of click
     */
    public boolean clickNode(Node n) {
        return board.revealNode(n);
    }

    /**
     * @return Returns board of game
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Flags tile by node. Tells gamestats if node was flagged or unflagged.
     *
     * @param n Node to be flagged
     */
    public void flagNode(Node n) {
        this.stats.addToFlagged(board.flagNode(n));
    }

    /**
     * Returns true if boards winstate is true.
     *
     * @return returns games win state
     */
    public boolean gameWon() {
        return board.hasGameBeenWon();
    }


    public int getBombsLeft() {
        return stats.getBombsLeft();
    }

    public long getTimeStarted() {
        return stats.getTimeStarted();
    }
}
