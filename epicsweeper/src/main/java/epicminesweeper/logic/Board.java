package epicminesweeper.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Board {

    private Node[][] nodes;
    private Map<Node, List<Node>> adjacent;
    private int height;
    private int width;
    private int bombs;
    private int correctlyFlagged;
    private int revealed;

    public Board(int height, int width, int bombs) {
        bombs = Math.min(height * width, bombs);
        init(height, width);
        this.bombs = 0;
        placeBombs(bombs);
    }

    // Creates board from seed, mostly for testing, maybe challenge levels?
    public Board(int height, int width, String seed) {
        init(height, width);
        createBoardFromSeed(seed);
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


    private void createBoardFromSeed(String seed) {
        int i = 0;
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (seed.charAt(i) == 'x') {
                    setNodeAsBomb(nodes[w][h]);
                }
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

    public void increaseNearbyCounts(Node node) {
        for (Node n : adjacent.get(node)) {
            n.increaseAdjBombs();
        }
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

    public boolean clickTile(int x, int y) {
        return revealNode(nodes[x][y]);
    }

    public boolean revealNode(Node n) {
        if (n.isRevealed()) {
            return true;
        }
        n.setRevealed(true);
        revealed++;
        if (n.isBomb()) {
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

    public void revealAll() {
        for (Node n : getListOfNodes()) {
            n.setRevealed(true);
        }
    }

    public boolean hasGameBeenWon() {
        return correctlyFlagged == bombs || (width * height) - revealed == bombs;
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

    public String exportBoard(Boolean gameMode) {
        StringBuilder sb = new StringBuilder();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                Node n = nodes[w][h];
                if (gameMode && !n.isRevealed()) {
                    sb.append("❑");
                } else if (n.isBomb()) {
                    sb.append("x");
                } else {
                    sb.append(n.getAdjBombs());
                }
            }
        }
        return sb.toString();
    }

    public int getBombs() {
        return bombs;
    }

    public void flagTile(int x, int y) {
        /*TODO: prevent out of bounds*/
        correctlyFlagged += nodes[x][y].toggleFlagged();
    }

    public void flagNode(Node node) {
        correctlyFlagged += node.toggleFlagged();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}