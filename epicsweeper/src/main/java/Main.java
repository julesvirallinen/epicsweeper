import epicminesweeper.logic.Board;
import epicminesweeper.logic.Game;

public class Main {

    public static void main(String[] args) {

//        Game g = new Game(10,15,10);
        Game g = new Game(6, 7, "" +
                "xoooooo" +
                "0oxoooo" +
                "xoxoooo" +
                "ooooxoo" +
                "ooooooo" +
                "ooooooo");
        Board b = g.getBoard();
        b.clickTile(6, 0);




    }
}
