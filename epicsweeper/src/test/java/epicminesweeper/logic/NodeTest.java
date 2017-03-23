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
    public void NodeToStringWorks(){
        Node b = new Node(true);
        b.setRevealed(true);
        Node r = new Node(false);
        r.setAdjBombs(6);
        r.setRevealed(true);
        Node n = new Node(false);
        assertTrue(b.toString().equals("x"));
        assertTrue(r.toString().equals("6"));
        assertTrue(n.toString().equals("❑"));

    }
}
