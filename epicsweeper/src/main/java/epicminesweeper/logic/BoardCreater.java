package epicminesweeper.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Initializes the board.
 */
public class BoardCreater {

    private int height;
    private int width;
    private Node[][] nodes;
    private int bombs;
    private Map<Node, List<Node>> adjacent;

    /**
     * Initializes board with width, height, bombs.
     *
     * @param height height of board
     * @param width  width of board
     * @param bombs  amount of bombs
     * @throws IllegalArgumentException if {@code bombs} is greater than the amount of nodes on the board, that is, {@code bombs > height*width}.
     */

    public BoardCreater(int height, int width, int bombs) {
        if (bombs > height * width) {
            throw new IllegalArgumentException("Cannot place more bombs than nodes on the board");
        }
        init(height, width);
        this.bombs = 0;
        placeBombs(bombs);
    }

    // Creates board from seed, mostly for testing, maybe challenge levels?

    /**
     * Creates board according to seed.
     *
     * @param height          height of board to be created
     * @param width           width of board to be created
     * @param serializedBoard Initializes board from serialized template, mainly for testing.
     *                        Example of 3x3 board with bomb in top corner.
     *                        oox
     *                        ooo
     *                        ooo
     */
    public BoardCreater(int height, int width, String serializedBoard) {
        init(height, width);
        createSerializedBoard(serializedBoard);
    }


    void init(int height, int width) {
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
    void createSerializedBoard(String seed) {
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

    public Node[][] getNodes() {
        return nodes;
    }

    public Map<Node, List<Node>> getAdjacent() {
        return adjacent;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getBombs() {
        return bombs;
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
