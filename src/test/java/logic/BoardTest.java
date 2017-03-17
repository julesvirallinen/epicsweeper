package logic;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class BoardTest {

    @Test
    public void boardHasCorrectAmountOfBombs() {
        int amount = 20;
        Board b = boardFactory(5, 5, amount);

        int count = 0;
        for (Node n : b.getListOfNodes()) {
            if (n.isBomb()) count++;
        }

        assertTrue(count == amount);
    }

    @Test
    public void boardHasCorrectAmountOfNodes() {
        int height = 5;
        int width = 7;

        Board b = boardFactory(5, 7, 0);

        assertTrue(b.getListOfNodes().size() == height * width);
    }

    @Test
    public void adjacentBombsWork() {
        Board b = boardFactory(2, 2, 3);

        int count = 0;
        for (Node n : b.getListOfNodes()) {
            if (n.getAdjBombs() == 3) count++;
        }

        b = boardFactory(2, 2, 1);

        count = 0;
        for (Node n : b.getListOfNodes()) {
            if (n.getAdjBombs() == 1) count++;
        }
        assertTrue(count == 3);
    }

    public Board boardFactory(int h, int w, int b) {
        Board board = new Board(h, w, b);
        board.init();
        return board;
    }


}
