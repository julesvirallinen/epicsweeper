package epicminesweeper.gui;

import epicminesweeper.logic.Node;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alt on 2.4.2017.
 */
public class JNode extends JLabel {

    private Node node;

    public JNode(Node n) {
        this(n.toString());
        node = n;
        if(!n.isRevealed()){
            setBackground(Color.GRAY);
        }
    }


    public JNode(String s) {
        super(s, SwingConstants.CENTER);
//        setMaximumSize(new Dimension(20, 20));


    }

    public Node getNode() {
        return node;
    }


    public void updateNode() {
        setText(node.toString());
        if (node.isRevealed()) {
            setBackground(Color.LIGHT_GRAY);
        }
    }
}
