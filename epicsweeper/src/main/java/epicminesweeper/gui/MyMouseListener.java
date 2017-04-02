package epicminesweeper.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by jules on 2.4.2017.
 */

public class MyMouseListener implements MouseListener {

    private MainGui gui;
//    private Board board;

    public MyMouseListener(MainGui mainGui) {
//        this.board = board;
        this.gui = mainGui;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JNode l = (JNode) e.getSource();
        gui.updateNodes(l);
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
