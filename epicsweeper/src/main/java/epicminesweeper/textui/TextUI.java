package epicminesweeper.textui;


import epicminesweeper.logic.Difficulty;
import epicminesweeper.logic.Game;

import java.util.Scanner;

public class TextUI {

    public Game game;

    public TextUI() {
        init();

    }

    public void init() {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Choose difficulty: (1-3)");
        int n = Integer.parseInt(reader.nextLine()); // Scans the next token of the input as an int.

        this.game = new Game(Difficulty.EASY);
        play(reader);
    }

    public void play(Scanner reader) {
        while (true) {
            printBoard();
            System.out.println("Enter action and x, then y-coordinate, like this: click 1 0");
            String input = reader.nextLine();
            String[] command = input.split("\\s+");
            int x = Integer.parseInt(command[1]);
            int y = Integer.parseInt(command[2]);
            if (command[0].equals("flag")) {
                game.flagTile(x, y);
            } else {
                Boolean cont = game.clickTile(x, y);
                if (!cont) {
                    System.out.println("Game over!");
                    printBoard();
                    break;
                }
            }
            if (game.gameWon()) {
                System.out.println("Congrats, you won!");
                break;
            }
        }
    }

    //  For development.
    public void printBoard() {

        int width = game.getBoard().getWidth();
        int height = game.getBoard().getHeight();
        System.out.print("    ");
        for (int w = 0; w < width; w++) {
            System.out.print(w + " ");
            if (w < 10) {
                System.out.print(" ");
            }
        }
        System.out.println();
        System.out.print("    ");

        for (int w = 0; w < width; w++) {
            System.out.print("_  ");
        }
        System.out.println();

        for (int h = 0; h < height; h++) {
            System.out.print(h + "| ");
            if (h < 10) {
                System.out.print(" ");
            }
            for (int w = 0; w < width; w++) {
                System.out.print(game.getBoard().getNodes()[w][h] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
