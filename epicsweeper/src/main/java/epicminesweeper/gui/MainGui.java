package epicminesweeper.gui;

import epicminesweeper.logic.Board;
import epicminesweeper.logic.Difficulty;
import epicminesweeper.logic.Game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainGui {

    private Game game;
    private JFrame frame;
    private JNode[][] grid;
    private JPanel bottomPanel;
    private JLabel flagged;
    private JButton newGame;
    private JLabel timer;
    private long startTime;
    private Timer sTimer;

    public MainGui() {
        init();

    }

    private void init() {
        this.frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);

        createTopPanel();

        frame.pack();
        frame.setVisible(true);

    }


    private void startGame(ActionEvent e) {
        createGameBoard();
        createTopPanel();
        updateBoard();
        frame.pack();

        this.sTimer = new Timer(500, e1 -> timer.setText((System.currentTimeMillis() - startTime) / 1000 + ""));
        sTimer.start();

    }


    private void createTopPanel() {
//        n.setPreferredSize(new Dimension(80, 30));
        this.bottomPanel = new JPanel();
        this.flagged = new JLabel("Welcome");
        this.timer = new JLabel("0");
        frame.getContentPane().add(bottomPanel, BorderLayout.PAGE_END);

        bottomPanel.add(flagged);
        bottomPanel.add(timer);

        JButton b1 = new JButton();
        b1.setSize(400, 400);
        b1.setVisible(true);
        b1.setText("New game");
        b1.setActionCommand("new game");
        b1.addActionListener(this::startGame);
        bottomPanel.add(b1);
    }


    private void createGameBoard() {
        this.game = new Game(Difficulty.INTERMEDIATE);
        this.startTime = game.getTimeStarted();
        frame.getContentPane().removeAll();
        Board b = game.getBoard();
        int width = b.getWidth();
        int height = b.getHeight();

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        MyMouseListener ml = new MyMouseListener();

        panel.setLayout(new GridLayout(width, height));
        this.grid = new JNode[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                JNode n = new JNode(b.getNodes()[i][j]);
                n.setBorder(new LineBorder(Color.BLACK));
                n.setOpaque(true);
                n.addMouseListener(ml);
                n.setPreferredSize(new Dimension(30, 30));
                grid[i][j] = n;
                panel.add(grid[i][j]);
            }
        }
    }

    public void updateBoard() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j].updateNode();
            }
        }
        updateFlagged();

    }

    private void updateFlagged() {
        this.flagged.setText(game.getBombsLeft() + " | " + (game.gameWon() ? "Game won" : "Game not won") + "| ");
    }

    public void clickNode(JNode node) {
        if (!game.clickNode(node.getNode())) {
            sTimer.stop();
        }
        updateBoard();

    }

    public void rightClickNode(JNode node) {
        game.flagNode(node.getNode());
        updateBoard();
    }

    private class MyMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            JNode l = (JNode) e.getSource();
            if (SwingUtilities.isLeftMouseButton(e)) {
                clickNode(l);
            } else if (SwingUtilities.isRightMouseButton(e)) {
                rightClickNode(l);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

}
