package epicminesweeper.logic;

/**
 * Manages a single tile. Knows if tile is bomb, knows how many tiles next to it are bombs.
 * Can be printed correctly for a text-based game. 
 * @author Julius Uusinarkaus
 */
public class Node {

    private boolean flagged;
    private boolean bomb;
    private int adjBombs;
    private boolean revealed;
    private boolean gameEnder;

    /**
     *
     * @param bomb
     */
    public Node(boolean bomb) {
        this.bomb = bomb;
        adjBombs = 0;
        revealed = false;
    }

    /**
     *
     * @return
     */
    public boolean isFlagged() {
        return flagged;
    }

    /**
     *
     * @param flagged: set's value of flagged to true or false.
     */
    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    /**
     *
     * Toggles flag status of tile, and
     * returns -1 if tile is bomb, and unflagged.
     * Returns 1 if tile is correctly flagged.
     * If a tile is not a bomb, returns -1, so that flagged amount is correct
     * @return
     */
    public int toggleFlagged() {
        flagged = !flagged;
        return flagged ? (bomb ? 1 : -1) : (bomb ? -1 : 1);
    }

    /**
     *
     * @param b
     */
    public void setGameEnder(Boolean b) {
        gameEnder = b;
    }

    /**
     *
     * @return
     */
    public boolean isGameEnder() {
        return gameEnder;

    }

    /**
     *
     * @return
     */
    public boolean isBomb() {
        return bomb;
    }

    /**
     *
     * @param bomb
     */
    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    /**
     *
     * @return
     */
    public int getAdjBombs() {
        return adjBombs;
    }

    /**
     *
     * @param adjBombs
     */
    public void setAdjBombs(int adjBombs) {
        this.adjBombs = adjBombs;
    }

    /**
     *
     */
    public void increaseAdjBombs() {
        adjBombs++;
    }

    /**
     *
     * @return
     */
    public boolean isRevealed() {
        return revealed;
    }

    /**
     *
     * @param revealed
     */
    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }


    @Override
    public String toString() {

        if (!revealed) {
            if (flagged) {
                return "⚑";
            }
            return "❑";
        }
        if (bomb) {
            return "x";
        }
        if (adjBombs == 0) {
            return " ";
        }
        return adjBombs + "";
    }
}
