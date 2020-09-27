package edu.umsl;
//import edu.umsl.NewGame;
import java.util.Scanner;

public class ConnectFour {
    public static void main(String[] args) {
        do {
            NewGame gameStats = new NewGame();
            greetingsAndColorChoice(gameStats);
            gameplay(gameStats);
            currentWins(gameStats);
        } while (playAgain());
    }

    public static void greetingsAndColorChoice(NewGame gameStats) {
        Scanner scanner = new Scanner(System.in);

        //Prompt for color choice
        System.out.println("Let's play Connect Four!");
        System.out.println("Player One please choose a color, red or yellow?");
        do {
            System.out.println("(Enter R for red or Y for yellow.)");
            gameStats.setPlayerOne(Character.toUpperCase(scanner.next().charAt(0)));
        } while (gameStats.getPlayerOne() != 'R' && gameStats.getPlayerOne() != 'Y');
        //Set color for playerTwo
        if (gameStats.getPlayerOne() == 'R') {
            gameStats.setPlayerTwo('Y');
            System.out.println("Player Two, you'll be Yellow.");
        } else {
            gameStats.setPlayerTwo('R');
            System.out.println("Player Two you'll be Red.");
        }
    }

    private static void gameplay(NewGame gameStats) {
        Scanner scanner = new Scanner(System.in);
        int player = 1;
        int columnChoice = -1;

        //NEED TRY/CATCH FOR NON-INT INPUTS!!!!!!!

        do {
            do {
                gameStats.printGameBoard();
                System.out.println("Player " + (player == 1 ? "One" : "Two") + " please choose a row (0 - 6):");
                columnChoice = scanner.nextInt();
            } while (columnChoice < 0 || columnChoice > 6);

            //update and print board
            if (player == 1) {
                gameStats.addPlayerOneGameplay(columnChoice);
                player++;
            } else {
                gameStats.addPlayerTwoGameplay(columnChoice);
                player--;
            }

            //if Player has won on this turn, break out from gameplay
            if (gameStats.getCurrentGameWon()) {
                break;
            }

            //reset columnChoice, switch players
            columnChoice = -1;

        } while (!gameStats.getCurrentGameWon());
    }

    private static void currentWins(NewGame gameStats) {
        System.out.println();
        System.out.println("Player One has won " + gameStats.getPlayerOneWinCount() + " time(s).");
        System.out.println("Player Two has won " + gameStats.getPlayerTwoWinCount() + " time(s).");
        System.out.println("Well done!");
        System.out.println();
    }

    private static Boolean playAgain() {
        Scanner scanner = new Scanner(System.in);
        char yesOrNo = ' ';

        do {
            System.out.println("Would you like to play again?");
            yesOrNo = Character.toUpperCase(scanner.next().charAt(0));
        } while (yesOrNo != 'Y' && yesOrNo != 'N');

        if (yesOrNo == 'N'){
            System.out.println("Thanks for playing!");
            System.exit(0);
        }
        //if yes
        return true;
    }

}
