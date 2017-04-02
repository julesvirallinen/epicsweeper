package epicminesweeper.logic;

public class Node {

    private boolean flagged;
    private boolean bomb;
    private int adjBombs;
    private boolean revealed;
    private boolean gameEnder;


    public Node(boolean bomb) {
        this.bomb = bomb;
        adjBombs = 0;
        revealed = false;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public int toggleFlagged() {
        /* Ehkä elämäni kaunein metodi. Flippaa flagged arvon, ja palauttaa -1 -> 1 riipuen liputtamisen oikeudesta*/
        flagged = !flagged;
        return flagged ? (bomb ? 1 : 0) : (bomb ? -1 : 0);
    }

    public void setGameEnder(Boolean b) {
        gameEnder = b;
    }

    public boolean isGameEnder() {
        return gameEnder;

    }

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
