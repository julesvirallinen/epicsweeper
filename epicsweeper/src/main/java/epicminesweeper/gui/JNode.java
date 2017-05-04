package epicminesweeper.gui;

import epicminesweeper.logic.Node;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by alt on 2.4.2017.
 */
public class JNode extends JLabel {

    private Node node;

    public JNode(Node n) {
        this("");
        node = n;
        updateNode();
    }


    public JNode(String s) {
        super(s, SwingConstants.CENTER);
//        setMaximumSize(new Dimension(20, 20));


    }

    public Node getNode() {
        return node;
    }


    public void updateNode() {
        if (!node.isRevealed()) {
            setBackground(Color.LIGHT_GRAY);
            setIcon(null);

        }

        if (node.isRevealed()) {
            setBackground(Color.WHITE);
            if (node.getGameEnder()) {
                setImage("boom");
            } else if (node.isBomb()) {
                setImage("bomb");
//            } else if (node.getAdjBombs() == 0) {
//                setText("");
            } else {
                setBackground(Color.WHITE);
//                setImage(node.getAdjBombs() + "");
                setText(node.toString());
            }
        } else if (node.getFlagged()) {
            setImage("flag");
        }
    }

    public void setImage(String name) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource("/" + name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(20, 20,
                Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(dimg));

    }
}
