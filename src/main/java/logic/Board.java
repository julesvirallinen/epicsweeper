package logic;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Board {

    private Node[][] nodes;
    private Map<Node, List<Node>> adjacent;
    private int height;
    private int width;
    private int bombs;

    public Board(int height, int width, int bombs) {
        this.height = height;
        this.width = width;
        this.bombs = bombs;


    }

    // Creates board from seed, mostly for testing, maybe challenge levels?
    public Board(int height, int width, String seed) {
        this.height = height;
        this.width = width;
        this.nodes = new Node[width + 1][height + 1];
        this.adjacent = new HashMap<>();

        initNodes();
        createBoardFromSeed(seed);

    }

    private void createBoardFromSeed(String seed) {
        // Oispa mod

        int i = 0;
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                if (seed.charAt(i) == 'x') setNodeAsBomb(nodes[w][h]);
                i++;
            }
        }
    }

    public void init() {
        this.nodes = new Node[width + 1][height + 1];
        this.adjacent = new HashMap<>();
        initNodes();
        placeBombs(bombs);

    }


    public void initNodes() {
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
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

    public void initAdjacent() {
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                setSingleAdjacent(w, h);
            }
        }
    }

    public void setSingleAdjacent(int w, int h) {
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


    public void increaseNearbyCounts(Node node) {
        for (Node n : adjacent.get(node)) {
            n.increaseAdjBombs();
        }
    }

    public List<Node> getListOfNodes() {
        ArrayList<Node> n = new ArrayList<>();
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                n.add(nodes[w][h]);
            }
        }
        return n;
    }

    public Node[][] getNodes() {
        return nodes;
    }

    //  For development.
    public void printBoard() {
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                System.out.print(nodes[w][h] + " ");
            }
            System.out.println();
        }
    }

    public String exportBoard() {
        StringBuilder sb = new StringBuilder();
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                Node n = nodes[w][h];
                if (n.isBomb()) sb.append("x");
                else sb.append(n.getAdjBombs());
            }
        }
        return sb.toString();
    }
}
