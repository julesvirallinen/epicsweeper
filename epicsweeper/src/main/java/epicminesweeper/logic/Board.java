package epicminesweeper.logic;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Board {

    private Node[][] nodes;
    private Map<Node, List<Node>> adjacent;
    private int height;
    private int width;
    private int bombs;

    public Board(int height, int width, int bombs) {
        bombs = Math.min(height * width, bombs);
        this.bombs = bombs;

        init(height, width);
        placeBombs(bombs);
        printBoard();


    }

    // Creates board from seed, mostly for testing, maybe challenge levels?
    public Board(int height, int width, String seed) {

        init(height, width);
        createBoardFromSeed(seed);
        printBoard();

    }

    private void init(int height, int width) {
        height = Math.max(1, height);
        width = Math.max(1, width);
        this.height = height;
        this.width = width;

        this.nodes = new Node[width + 1][height + 1];
        this.adjacent = new HashMap<>();
        initNodes();

    }


    private void createBoardFromSeed(String seed) {
        // Oispa mod

        int i = 0;
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (seed.charAt(i) == 'x') setNodeAsBomb(nodes[w][h]);
                i++;
            }
        }
    }


    private void initNodes() {
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                nodes[w][h] = new Node(false);
            }
        }
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

    private void setNodeAsBomb(Node n) {
        n.setBomb(true);
        increaseNearbyCounts(n);
    }

    // gives two random integers that fit on map
    private int[] rnd() {
        int[] locations = new int[2];
        locations[0] = ThreadLocalRandom.current().nextInt(0, width);
        locations[1] = ThreadLocalRandom.current().nextInt(0, height);
        return locations;
    }

    private void initAdjacent() {
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {

                setSingleAdjacent(w, h);
            }
        }
    }

    private void setSingleAdjacent(int w, int h) {
        ArrayList<Node> adj = new ArrayList<>();
        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {

                // Skips current node
                if (a == 0 && b == 0) continue;

                // Skips nodes that are off-map
                if (w + a < 0 || w + a > width - 1 || h + b < 0 || h + b > height - 1) continue;

                // else adds node to list of nearby nodes
                adj.add(nodes[w + a][h + b]);
            }
        }
        this.adjacent.put(nodes[w][h], adj);

    }

    public void clickTile(int x, int y) {
        clickNode(nodes[x][y]);
        printBoard();

    }

    public void clickNode(Node n) {
        if (n.isRevealed()) {
            return;
        }
        n.setRevealed(true);

        if (n.isBomb()) {
            System.out.println("BOOM");
        }
        if (n.getAdjBombs() == 0) {
            for (Node a : adjacent.get(n)) {
                clickNode(a);
            }
        }

    }


    public void increaseNearbyCounts(Node node) {
        for (Node n : adjacent.get(node)) {
            n.increaseAdjBombs();
        }
    }

    public List<Node> getListOfNodes() {
        ArrayList<Node> n = new ArrayList<>();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                n.add(nodes[w][h]);
            }
        }
        return n;
    }

    public Node[][] getNodes() {
        return nodes;
    }

    //  For development.
    private void printBoard() {
        System.out.print("   ");
        for (int w = 0; w < width; w++) {
            System.out.print(w + " ");
        }
        System.out.println();
        System.out.print("   ");

        for (int w = 0; w < width; w++) {
            System.out.print("_ ");
        }
        System.out.println();

        for (int h = 0; h < height; h++) {
            System.out.print(h + "| ");
            for (int w = 0; w < width; w++) {
                System.out.print(nodes[w][h] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public String exportBoard() {
        StringBuilder sb = new StringBuilder();
        for (int h = 0; h < height; h++) {

            for (int w = 0; w < width; w++) {
                Node n = nodes[w][h];
                if (n.isBomb()) sb.append("x");
                else sb.append(n.getAdjBombs());
            }
        }
        return sb.toString();
    }
}
