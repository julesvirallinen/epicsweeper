package epicminesweeper.logic;

/**
 * Manages a single tile. Knows if tile is bomb, knows how many tiles next to it are bombs.
 * Can be printed correctly for a text-based game.
 *
 * @author Julius Uusinarkaus
 */
public class Node {

    private boolean flagged;
    private boolean bomb;
    private int adjBombs;
    private boolean revealed;
    private boolean gameEnder;

    /**
     * Creates a new node.
     *
     * @param bomb Determines if node is created as bombs.
     */
    Node(boolean bomb) {
        this.bomb = bomb;
        adjBombs = 0;
        revealed = false;
    }


    public boolean getFlagged() {
        return flagged;
    }


    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    /**
     * Toggles flag status of tile, and
     * returns -1 if tile is bomb, and unflagged.
     * Returns 1 if tile is correctly flagged.
     * If a tile is not a bomb, returns -1, so that flagged amount is correct
     *
     * @return ^
     */
    int toggleFlagged() {
        flagged = !flagged;
        return flagged ? (bomb ? 1 : -1) : (bomb ? -1 : 1);
    }


    public boolean getGameEnder() {
        return gameEnder;

    }

    /**
     * Sets this node as node that ended game. Used for GUI.
     *
     * @param b Sets this node as node that ended game.
     */
    void setGameEnder(Boolean b) {
        gameEnder = b;
    }

    /**
     * Returns if node is a bomb.
     *
     * @return true if this node is bomb.
     */
    public boolean isBomb() {
        return bomb;
    }


    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }


    public int getAdjBombs() {
        return adjBombs;
    }


    public void setAdjBombs(int adjBombs) {
        this.adjBombs = adjBombs;
    }

    /**
     * Increases amount of adjascent bombs by one.
     */
    public void increaseAdjBombs() {
        adjBombs++;
    }


    public boolean isRevealed() {
        return revealed;
    }


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
