package epicminesweeper.logic;

/**
 * Manages stats for a game.
 *
 * @author Julius Uusinarkaus
 */
public class GameStats {

    private long timeStarted;
    private int bombs;
    private int flagged;
    private int clicks;

    /**
     * Initializes stats.
     *
     * @param bombs Amount of bombs on board
     */
    public GameStats(int bombs) {
        this.bombs = bombs;
        this.timeStarted = System.currentTimeMillis();
    }

    /**
     * Adds value to flagged, according to flag-logic.
     *
     * @param n value to be added (-1 -> 1)
     */
    public void addToFlagged(int n) {
        this.flagged += n;
    }

    /**
     * Returns amount of bombs left on board.
     *
     * @return Bombs left on board
     */
    public int getBombsLeft() {
        return this.bombs - this.flagged;
    }

    /**
     * Returns time game was started.
     *
     * @return long time game started
     */
    public long getTimeStarted() {
        return timeStarted;
    }
}
