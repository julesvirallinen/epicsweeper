package epicminesweeper.logic;


public class GameStats {

    private long timeStarted;
    private int bombs;
    private int flagged;
    private int clicks;


    public GameStats(int bombs) {
        this.bombs = bombs;
        this.timeStarted = System.currentTimeMillis();
    }



    public void addToFlagged(int n) {
        this.flagged += n;
    }

    public int getBombsLeft() {
        return this.bombs - this.flagged;
    }

    public long getTimeStarted() {
        return timeStarted;
    }
}
