package epicminesweeper.logic;

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
        Board b1 = new Board(3, 3, "ooxoooooo");
        assertTrue(b1.exportBoard().equals("01x011000"));

        Board b2 = new Board(3, 3, "xxxxoxxxx");
        assertTrue(b2.exportBoard().equals("xxxx8xxxx"));

        Board b3 = new Board(3, 3, "" +
                "xoo" +
                "0ox" +
                "x0x");
        assertTrue(b3.exportBoard().equals("x2124xx3x"));

    }

    @Test
    public void allTilesAreHiddenAtFirst() {
        Board b = boardFactory(10, 10, 10);
        for (Node n : b.getListOfNodes()) {
            assertTrue(!n.isRevealed());
        }
    }

    public Board boardFactory(int h, int w, int b) {
        Board board = new Board(h, w, b);
        return board;
    }


}
