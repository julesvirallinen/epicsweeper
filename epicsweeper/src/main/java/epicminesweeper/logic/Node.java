package epicminesweeper.logic;

/**
 * Manages a single tile. Knows if tile is bomb, knows it's adjascent tiles.
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
     * @param flagged
     */
    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    /**
     *
     * @return
     */
    public int toggleFlagged() {
        /* Ehkä elämäni kaunein metodi. Flippaa flagged arvon, ja palauttaa -1 -> 1 riipuen liputtamisen oikeudesta*/
        flagged = !flagged;
        return flagged ? (bomb ? 1 : 0) : (bomb ? -1 : 0);
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
