package epicminesweeper.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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

    public Board(int height, int width, int bombs) {
        if (bombs > height * width) {
            throw new IllegalArgumentException("Cannot place more bombs than nodes on the board");
        }
        init(height, width);
        this.bombs = 0;
        placeBombs(bombs);
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
    public Board(int height, int width, String serializedBoard) {
        init(height, width);
        createSerializedBoard(serializedBoard);
    }

    /**
     * Initializes board. Sets local height and board from constructors,
     * creates map of nearby nodes, and array of nodes.
     *
     * @param height height of board to be created
     * @param width  width of board to be created
     */
    private void init(int height, int width) {
        height = Math.max(1, height);
        width = Math.max(1, width);
        this.height = height;
        this.width = width;
        this.nodes = new Node[width][height];
        this.adjacent = new HashMap<>();
        initNodes();
    }

    /**
     * Creates board from serial. If next letter is x, next node is bomb.
     */
    private void createSerializedBoard(String seed) {
        foreachCell((w, h, i) -> {
            if (seed.charAt(i) == 'x') {
                setNodeAsBomb(nodes[w][h]);
            }
        });
    }

    /**
     * Creates the nodes for the game, then calls setSingleAdjacent for each Node.
     */
    private void initNodes() {
        foreachCell((w, h) -> nodes[w][h] = new Node(false));
        foreachCell(this::setSingleAdjacent);
    }

    /**
     * Creates an array of adjacent nodes for each node.
     *
     * @param w width coordinate of node to be handled
     * @param h height coordinate
     */

    private void setSingleAdjacent(int w, int h) {
        ArrayList<Node> adj = new ArrayList<>();
        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                if (a == 0 && b == 0) {
                    continue;
                }
                if (w + a < 0 || w + a > width - 1 || h + b < 0 || h + b > height - 1) {
                    continue;
                }
                adj.add(nodes[w + a][h + b]);
            }
        }
        this.adjacent.put(nodes[w][h], adj);
    }

    /**
     * Places correct amount of bombs on board. Gets random coordinates, tries to change to bomb.
     * If node is already a bomb, gets new coordinates.
     */
    private void placeBombs(int amount) {
        for (int i = 0; i < amount; i++) {
            int[] location = rnd();
            Node n = nodes[location[0]][location[1]];
            if (n.isBomb()) {
                i--;
                continue;
            }
            setNodeAsBomb(n);
        }
    }

    /**
     * Generates random coordinates for placing bombs.
     *
     * @return array with coordinates
     */

    private int[] rnd() {
        int[] locations = new int[2];
        locations[0] = ThreadLocalRandom.current().nextInt(0, width);
        locations[1] = ThreadLocalRandom.current().nextInt(0, height);
        return locations;
    }

    /**
     * Sets single node as bomb. Increases the amount of adjascent bombs for nearby nodes.
     *
     * @param n Node to be set
     */
    private void setNodeAsBomb(Node n) {
        n.setBomb(true);
        increaseNearbyCounts(n);
        bombs++;
    }

    /**
     * Increases the amount of adjascent bombs in nearby nodes.
     *
     * @param node to increase for.
     */

    private void increaseNearbyCounts(Node node) {
        for (Node n : adjacent.get(node)) {
            n.increaseAdjBombs();
        }
    }


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
     * Reveals node
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
     * Calculates if game has been won
     *
     * @return true if game has been won
     */
    public boolean hasGameBeenWon() {
        return correctlyFlagged == bombs || (width * height) - revealed == bombs;
    }

    /**
     * Returns list of nodes in board.
     *
     * @return list of nodes.
     */
    public List<Node> getListOfNodes() {
        ArrayList<Node> n = new ArrayList<>();
        foreachCell((w, h) -> n.add(nodes[w][h]));
        return n;
    }

    /**
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
    public String exportBoard(Boolean gameMode) {
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
     * @return
     */
    public int getBombs() {
        return bombs;
    }

    /**
     * Used for testing, flags by coordinate
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
     * @param node
     */
    public int flagNode(Node node) {
        correctlyFlagged += node.toggleFlagged();
        return node.isFlagged() ? 1 : -1;
    }

    /**
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return
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