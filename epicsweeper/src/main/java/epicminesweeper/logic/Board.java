package epicminesweeper.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Keeps track of actual game. Had nodes and initialized board, and handles logic of playing
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
     * @param height
     * @param width
     * @param bombs
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
     *
     * @param height
     * @param width
     * @param serializedBoard
     */
    public Board(int height, int width, String serializedBoard) {
        init(height, width);
        createSerializedBoard(serializedBoard);
    }

    private void init(int height, int width) {
        height = Math.max(1, height);
        width = Math.max(1, width);
        this.height = height;
        this.width = width;
        this.nodes = new Node[width][height];
        this.adjacent = new HashMap<>();
        initNodes();
    }

    private void createSerializedBoard(String seed) {
        foreachCell((w, h, i) -> {
            if (seed.charAt(i) == 'x') {
                setNodeAsBomb(nodes[w][h]);
            }
        });
    }

    private void initNodes() {
        foreachCell((w, h) -> nodes[w][h] = new Node(false));
        initAdjacent();
    }

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

    private int[] rnd() {
        int[] locations = new int[2];
        locations[0] = ThreadLocalRandom.current().nextInt(0, width);
        locations[1] = ThreadLocalRandom.current().nextInt(0, height);
        return locations;
    }

    private void setNodeAsBomb(Node n) {
        n.setBomb(true);
        increaseNearbyCounts(n);
        bombs++;
    }

    private void increaseNearbyCounts(Node node) {
        for (Node n : adjacent.get(node)) {
            n.increaseAdjBombs();
        }
    }


    private void initAdjacent() {
        foreachCell(this::setSingleAdjacent);
    }

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
     *
     * @param x
     * @param y
     * @return
     */
    public boolean clickTile(int x, int y) {
        return revealNode(nodes[x][y]);
    }

    /**
     *
     * @param n
     * @return
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
     *
     */
    public void revealAll() {
        for (Node n : getListOfNodes()) {
            n.setRevealed(true);
        }
    }

    /**
     *
     * @return
     */
    public boolean hasGameBeenWon() {
        return correctlyFlagged == bombs || (width * height) - revealed == bombs;
    }

    /**
     *
     * @return
     */
    public List<Node> getListOfNodes() {
        ArrayList<Node> n = new ArrayList<>();
        foreachCell((w, h) -> n.add(nodes[w][h]));
        return n;
    }

    /**
     *
     * @return
     */
    public Node[][] getNodes() {
        return nodes;
    }

    /**
     *
     * @param gameMode
     * @return
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
     *
     * @return
     */
    public int getBombs() {
        return bombs;
    }

    /**
     *
     * @param x
     * @param y
     */
    public void flagTile(int x, int y) {
        /*TODO: prevent out of bounds*/
        correctlyFlagged += nodes[x][y].toggleFlagged();
    }

    /**
     *
     * @param node
     */
    public void flagNode(Node node) {
        correctlyFlagged += node.toggleFlagged();
    }

    /**
     *
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     *
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