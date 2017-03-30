package epicminesweeper.logic;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
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
        assertTrue(b1.exportBoard(false).equals("01x011000"));

        Board b2 = new Board(3, 3, "xxxxoxxxx");
        assertTrue(b2.exportBoard(false).equals("xxxx8xxxx"));

        Board b3 = new Board(3, 3, "" +
                "xoo" +
                "0ox" +
                "x0x");
        assertTrue(b3.exportBoard(false).equals("x2124xx3x"));

    }

    @Test
    public void allTilesAreHiddenAtFirst() {
        Board b = boardFactory(10, 10, 10);
        for (Node n : b.getListOfNodes()) {
            assertTrue(!n.isRevealed());
        }
    }

    @Test
    public void clickingOnZeroSpreads() {
        Board b = new Board(6, 7, "" +
                "xoooooo" +
                "ooxoooo" +
                "xoxoooo" +
                "ooooxoo" +
                "ooooooo" +
                "ooooooo");
        b.clickTile(6, 0);
        assertTrue(b.exportBoard(true).equals("❑❑❑1000❑❑❑2000❑❑❑31101212❑1000011100000000"));
    }

    @Test
    public void clickingOnNumberOnlyRevealsNumber() {
        Board b = new Board(6, 7, "" +
                "xoooooo" +
                "ooxoooo" +
                "xoxoooo" +
                "ooooxoo" +
                "ooooooo" +
                "ooooooo");
        b.clickTile(3, 0);
        assertTrue(b.exportBoard(true).equals("❑❑❑1❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑❑"));
    }

    @Test
    public void gameIsWonIfAllBombsFlagged() {
        Board b = new Board(4, 4, "" +
                "xoox" +
                "oooo" +
                "oooo" +
                "xoox");
        assertFalse(b.hasGameBeenWon());
        b.flagTile(0,0);
        b.flagTile(3, 0);
        b.flagTile(0, 3);
        assertFalse(b.hasGameBeenWon());
        b.flagTile(3, 3);
        assertTrue(b.hasGameBeenWon());
    }

    @Test
    public void GameIsWonIfAllNonBombNodesHaveBeenRevealed(){
        Board b = new Board(2, 2, "oxoo");
        assertFalse(b.hasGameBeenWon());
        b.clickTile(0, 0);
        b.clickTile(1, 1);
        assertFalse(b.hasGameBeenWon());
        b.clickTile(0, 1);
        assertTrue(b.hasGameBeenWon());
    }

    @Test
    public void clickingOnBombRevealsBoard(){
        Board b = new Board(4, 4, "xooxooooooooooox");
        b.clickTile(0, 0);
        for (Node n : b.getListOfNodes()) {
            assertTrue(n.isRevealed());
        }
    }

    @Test
    public void noBombsClickRevealsBoard() {
        Board b = new Board(4, 4, "" +
                "oooo" +
                "oooo" +
                "oooo" +
                "oooo");
        b.clickTile(0, 0);
        assertTrue(b.exportBoard(true).equals("0000000000000000"));
    }

    public Board boardFactory(int h, int w, int b) {
        Board board = new Board(h, w, b);
        return board;
    }


}
