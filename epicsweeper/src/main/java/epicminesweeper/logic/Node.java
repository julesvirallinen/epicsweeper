package epicminesweeper.logic;

public class Node {

    private boolean flagged;
    private boolean bomb;
    private int adjBombs;
    private boolean revealed;


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
            return "❑";
        }
        if (bomb) {
            return "x";
        }
        return adjBombs + "";
    }
}
