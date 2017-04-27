package epicminesweeper.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * x
 * Keeps track of actual game. Had nodes and initialized board, and handles logic of playing
 *
 * @author Julius Uusinarkaus
 */
public class Board {

    private Node[][] nodes;
    private Map<Node, List<Node>> adjacent;
    private int height;
    private int width;
    private int bombs;
    private int correctlyFlagged;
    private int revealed;

    /**
     * Initializes board with width, height, bombs.
     *
     * @param height height of board
     * @param width  width of board
     * @param bombs  amount of bombs
     * @throws IllegalArgumentException if {@code bombs} is greater than the amount of nodes on the board, that is, {@code bombs > height*width}.
     */

    Board(int height, int width, int bombs) {
        BoardCreater bc = new BoardCreater(height, width, bombs);
        getValues(bc);
    }

    // Creates board from seed, mostly for testing, maybe challenge levels?

    /**
     * @param height          height of board to be created
     * @param width           width of board to be created
     * @param serializedBoard Initializes board from serialized template, mainly for testing.
     *                        Example of 3x3 board with bomb in top corner.
     *                        oox
     *                        ooo
     *                        ooo
     */
    Board(int height, int width, String serializedBoard) {
        BoardCreater bc = new BoardCreater(height, width, serializedBoard);
        getValues(bc);

    }

    private void getValues(BoardCreater boardCreater) {
        this.nodes = boardCreater.getNodes();
        this.adjacent = boardCreater.getAdjacent();
        this.height = boardCreater.getHeight();
        this.width = boardCreater.getWidth();
        this.bombs = boardCreater.getBombs();
    }

    /**
     * Initializes board. Sets local height and board from constructors,
     * creates map of nearby nodes, and array of nodes.
     *
     * @param height height of board to be created
     * @param width  width of board to be created
     */


    /**
     * Reveals node that is clicked.
     *
     * @param x coordinate for node to be clicked
     * @param y coordinate for node to be clicked
     * @return returns false if node was bomb
     */
    public boolean clickTile(int x, int y) {
        return revealNode(nodes[x][y]);
    }

    /**
     * Reveals single node.
     *
     * @param n Node to be revealed
     * @return Returns false if node was bomb
     */
    public boolean revealNode(Node n) {
        if (n.isRevealed()) {
            return true;
        }
        n.setRevealed(true);
        revealed++;
        if (n.isBomb()) {
            n.setGameEnder(true);
            this.revealAll();
            return false;
        }
        if (n.getAdjBombs() == 0) {
            for (Node a : adjacent.get(n)) {
                revealNode(a);
            }
        }
        return true;
    }

    /**
     * Reveals all nodes. Used if game is over.
     */
    public void revealAll() {
        for (Node n : getListOfNodes()) {
            n.setRevealed(true);
        }
    }

    /**
     * Calculates if game has been won.
     *
     * @return true if game has been won
     */
    public boolean hasGameBeenWon() {
        return (width * height) - revealed == bombs;
    }

    /**
     * Returns list of nodes in board.
     *
     * @return list of nodes.
     */
    List<Node> getListOfNodes() {
        ArrayList<Node> n = new ArrayList<>();
        foreachCell((w, h) -> n.add(nodes[w][h]));
        return n;
    }

    /**
     * Returns array of all nodes.
     *
     * @return Returns array of nodes
     */
    public Node[][] getNodes() {
        return nodes;
    }

    /**
     * Exports board to string, mainly for testing.
     *
     * @param gameMode For exporting with bombs showing or hidden
     * @return String of game
     */
    String exportBoard(Boolean gameMode) {
        StringBuilder sb = new StringBuilder();
        foreachCell((w, h) -> {
            Node n = nodes[w][h];
            if (gameMode && !n.isRevealed()) {
                sb.append("❑");
            } else if (n.isBomb()) {
                sb.append("x");
            } else {
                sb.append(n.getAdjBombs());
            }
        });
        return sb.toString();
    }

    /**
     * Returns amount of bombs in game.
     *
     * @return Amount of bombs
     */
    int getBombs() {
        return bombs;
    }

    /**
     * Used for testing, flags by coordinate.
     *
     * @param x x-coord
     * @param y y-coord
     */
    public void flagTile(int x, int y) {
        correctlyFlagged += nodes[x][y].toggleFlagged();
    }

    /**
     * Returns 1/-1 depending on if tile was already flagged.
     * Also keeps track of correctly flagged nodes in game.
     *
     * @param node Node to be flagged.
     * @return -1 if node was already flagged, 1 if newly flagged, to keep count of flagged nodes.
     */
    public int flagNode(Node node) {
        correctlyFlagged += node.toggleFlagged();
        return node.getFlagged() ? 1 : -1;
    }

    /** Width getter.
     *
     * @return width of board
     */
    public int getWidth() {
        return width;
    }

    /**
     * Height getter.
     *
     * @return height of board
     */
    public int getHeight() {
        return height;
    }

    private void foreachCell(TableForeachCallback callback) {
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                callback.apply(w, h);
            }
        }
    }

    private void foreachCell(TableForeachCallbackWithOrdinal callback) {
        int i = 0;
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                callback.apply(w, h, i++);
            }
        }
    }

    @FunctionalInterface
    private interface TableForeachCallback {
        void apply(int w, int h);
    }

    @FunctionalInterface
    private interface TableForeachCallbackWithOrdinal {
        void apply(int w, int h, int i);
    }
}