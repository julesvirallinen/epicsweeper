package epicminesweeper.logic;


import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GameTest {

    @Test
    public void gameCanBeCreatedFromSeed(){
        Game g = new Game(3, 3, "oxooxooxo");
        assertTrue(g.getBoard().getListOfNodes().size() == 9);

    }

    @Test
    public void boardIsCreatedCorrectly(){
        Game g = new Game(3, 3, 1);
        assertTrue(g.getBoard().getListOfNodes().size() == 9);
        int bombs = 0;
        for (Node n : g.getBoard().getListOfNodes()) {
            if(n.isBomb()) bombs++;
        }
        assertTrue(bombs == 1);

    }

    @Test
    public void boardIsCreated(){
        Game g = new Game(5, 5, 5);
        assertTrue(g.getBoard().getListOfNodes().size() == 25);
    }
}
