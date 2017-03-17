package logic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private Node[][] nodes;
    private Map<Node, List<Node>> adjacent;
    private int height;
    private int width;

    public Board(int height, int width, int bombs) {
        this.height = height;
        this.width = width;
        this.nodes = new Node[width + 1][height + 1];
        this.adjacent = new HashMap<>();
        initNodes();
        initAdjacent();

        setNearbyCounts();
    }

    public void initNodes() {
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {

                if ((w == 3 && h == 3) || (w == 3 && h == 4)) {
                    nodes[w][h] = new Node(true);

                } else {
                    nodes[w][h] = new Node(false);

                }
            }
        }
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

    public void setNearbyCounts() {
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                setNodeNearbyCount(w, h);
            }
        }
    }

    public void setNodeNearbyCount(int w, int h) {
        Node cur = nodes[w][h];
        if (cur.isBomb()) {
            cur.setAdjBombs(0);
        }
        for (Node n : adjacent.get(cur)) {
            if (n.isBomb()) {
                cur.increaseAdjBombs();
            }
        }
    }


    //  For development.
    public void printBoard() {
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                System.out.print(nodes[w][h]);
            }
            System.out.println();
        }

    }
}
