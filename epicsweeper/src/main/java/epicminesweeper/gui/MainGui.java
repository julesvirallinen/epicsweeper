package epicminesweeper.gui;

import epicminesweeper.logic.Board;
import epicminesweeper.logic.Game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MainGui {

    private Game game;
    private JFrame frame;
    private JNode[][] grid;

    public MainGui() {
        this.game = new Game(3);
        init();

    }

    private void init() {
        this.frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createNodes();

        frame.pack();
        frame.setVisible(true);

    }

    private void createNodes() {

        Board b = game.getBoard();
        int width = b.getWidth();
        int height = b.getHeight();

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        MyMouseListener ml = new MyMouseListener(this);

        panel.setLayout(new GridLayout(width, height));
        this.grid = new JNode[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                JNode n = new JNode(b.getNodes()[i][j]);
                n.setBorder(new LineBorder(Color.BLACK));
                //grid[i][j].setBackground(Color.black);
                n.setOpaque(true);
                n.addMouseListener(ml);
                n.setMinimumSize(new Dimension(40, 40));
                grid[i][j] = n;
                panel.add(grid[i][j]);
            }
        }
    }

    public void updateNodes() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j].updateNode();
            }
        }
    }

    public void clickNode(JNode node) {
        game.getBoard().revealNode(node.getNode());
        updateNodes();

    }

    public void rightClickNode(JNode node) {
        game.getBoard().flagNode(node.getNode());
        updateNodes();
    }
}
