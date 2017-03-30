package epicminesweeper.logic;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class NodeTest {

    @Test
    public void NodeSetAsBombIsBomb() {
        Node bomb = new Node(true);
        assertTrue(bomb.isBomb());
    }

    @Test
    public void createdNodeisHidden(){
        Node n = new Node(false);
        assertTrue(!n.isRevealed());
    }

    @Test
    public void nodeToStringWorks(){
        Node b = new Node(true);
        b.setRevealed(true);
        Node r = new Node(false);
        r.setAdjBombs(6);
        r.setRevealed(true);
        Node n = new Node(false);
        assertTrue(b.toString().equals("x"));
        assertTrue(r.toString().equals("6"));
        assertTrue(n.toString().equals("❑"));
        n.toggleFlagged();
        assertTrue(n.toString().equals("⚑"));
    }

    @Test
    public void toggleNodeReturnsZeroIfNotBomb(){
        Node n = new Node(false);
        assertTrue(n.toggleFlagged() == 0);
    }

    @Test
    public void toggleNodeReturnsOneIfBombIsFlagged(){
        Node n = new Node(true);
        assertTrue(n.toggleFlagged() == 1);
    }

    @Test
    public void toggleNodeReturnsMinusOneIfBombUnflagged(){
        Node n = new Node(true);
        n.toggleFlagged();
        assertTrue(n.toggleFlagged() == -1);
    }

    @Test
    public void isFlaggedWorks(){
        Node n = new Node(true);
        n.toggleFlagged();
        assertTrue(n.isFlagged());
    }




}
