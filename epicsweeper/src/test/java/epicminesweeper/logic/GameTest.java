package epicminesweeper.logic;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {

    @Test
    public void gameCanBeCreatedFromSeed() {
        Game g = new Game(3, 3, "oxooxooxo");
        assertTrue(g.getBoard().getListOfNodes().size() == 9);
    }

    @Test
    public void boardIsCreatedCorrectly() {
        Game g = new Game(3, 3, 1);
        assertTrue(g.getBoard().getListOfNodes().size() == 9);
        int bombs = 0;
        for (Node n : g.getBoard().getListOfNodes()) {
            if (n.isBomb()) bombs++;
        }
        assertTrue(bombs == 1);

    }

    @Test
    public void boardIsCreated() {
        Game g = new Game(5, 5, 5);
        assertTrue(g.getBoard().getListOfNodes().size() == 25);
    }

    @Test
    public void clickNodeReturnsFalseIfGameIsLost() {
        Game g = new Game(1, 1, "x");
        Node n = g.getBoard().getListOfNodes().get(0);
        assertFalse(g.clickNode(n));

    }

    @Test
    public void getBombsLeftWorks() {
        Game g = new Game(5, 5, 25);
        assertTrue(g.getBombsLeft() == 25);
        g.flagNode(g.getBoard().getNodes()[0][0]);
        assertTrue(g.getBombsLeft() == 24);
    }

    @Test
    public void gameStartedTimeCorrect() {
        Game g = new Game(1, 1, 1);
        assertTrue(System.currentTimeMillis() - g.getTimeStarted() < 10);
    }

    @Test
    public void gameIsStartedWithCorrectSize() {
        Game g = new Game(Difficulty.EASY);
        assertTrue(g.getBoard().getListOfNodes().size() == 100);

        Game d = new Game(Difficulty.INTERMEDIATE);
        assertTrue(d.getBoard().getListOfNodes().size() == 400);

        Game f = new Game(Difficulty.HARD);
        assertTrue(f.getBoard().getListOfNodes().size() == 625);
    }


}
