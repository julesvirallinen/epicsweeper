package epicminesweeper.logic;


public class Game {

    private Board board;

    public Game(int height, int width, int bombs) {
        this.board = new Board(height, width, bombs);

    }

    public Game(int height, int width, String seed) {
        this.board = new Board(height, width, seed);
//        System.out.println(board.exportBoard());
    }

    public Board getBoard(){
        return board;
    }

}
