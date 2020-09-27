package edu.umsl;

import lombok.Getter;
import lombok.Setter;

public class NewGame {
    @Getter
    private char[][] gameBoard = initializeGameBoard();
    @Getter
    private Boolean currentGameWon = false;

    //player colors
    @Getter @Setter
    private char playerOne;
    @Getter @Setter
    private char playerTwo;

    //total wins per player
    @Getter
    private int playerOneWinCount = 0;
    @Getter
    private int playerTwoWinCount = 0;

    //turn counts >= 4 checked for horizontal/vertical wins
    //counts >=6 checked for diagonal wins
    private int playerOneTurnCount = 0;
    private int playerTwoTurnCount = 0;

    public char[][] initializeGameBoard () {
        gameBoard = new char [7][15];
        int colNumInAscii = 48; //ascii for number 0

        for (int row = 0; row < 7; row++){
            for(int col = 0; col < 15; col++) {
                //top row numbered 0 - 6 with whitespace between digits
                if (row == 0) {
                    if (col % 2 == 1) {
                        gameBoard[row][col] = ((char) colNumInAscii);
                        colNumInAscii++;
                    } else {
                        gameBoard[row][col] = ' ';
                    }
                }
                //even elements get divider lines, odd get whitespace
                else if (col % 2 == 0){
                    gameBoard[row][col] = '|';
                } else {
                    gameBoard[row][col] = ' ';
                }
            }
        }
        return gameBoard;
    }

    //prints gameBoard with dotted line beneath
    public void printGameBoard() {
        for (int row = 0; row < 7; row++){
            for(int col = 0; col < 15; col++){
                System.out.print(gameBoard[row][col]);
            }
            System.out.println();
        }
        System.out.println("---------------");
    }

    public void addPlayerOneGameplay(int columnChoice) {
        //player choice adjusted to match board labeling
        int choiceAdjustedToBoard = columnChoice * 2 + 1;

        for (int row = 6; row >= 0; row--) {
            //if a player has played in this column,
            //then place their color up from a row from the last color played
            if (gameBoard[row][choiceAdjustedToBoard] == ' ') {
                gameBoard[row][choiceAdjustedToBoard] = playerOne;
                break;
            }
        }
        playerOneTurnCount++;

        //check for a win for player one
        if (playerOneTurnCount >= 4) {
            if (checkForHorizontalWin(playerOne) || checkForVerticalWin(playerOne)) {
                printGameBoard();
                System.out.println("Player One wins!");
                playerOneWinCount++;
                currentGameWon = true;
            }
            if (playerOneTurnCount >=6) {
                if (checkForForwardDiagonalWin(playerOne) || checkForBackwardsDiagonalWin(playerOne)){
                    printGameBoard();
                    System.out.println("Player One wins!");
                    playerOneWinCount++;
                    currentGameWon = true;
                }
            }
        }
    }

    public void addPlayerTwoGameplay(int columnChoice) {
        //player choice adjusted to match board labeling
        int choiceAdjustedToBoard = columnChoice * 2 + 1;

        //if a player has played in this column,
        //then place their color up from a row from the last color played
        for (int row = 6; row >= 0; row--) {
            if (gameBoard[row][choiceAdjustedToBoard] == ' ') {
                gameBoard[row][choiceAdjustedToBoard] = playerTwo;
                break;
            }
        }
        playerTwoTurnCount++;

        //check for a win for player two
        if (playerTwoTurnCount >= 4) {
            if (checkForHorizontalWin(playerTwo) || checkForVerticalWin(playerTwo)) {
                printGameBoard();
                System.out.println("Player Two wins!");
                playerTwoWinCount++;
                currentGameWon = true;
            }
            if (playerOneTurnCount >=6) {
                if (checkForForwardDiagonalWin(playerTwo) || checkForBackwardsDiagonalWin(playerTwo)){
                    printGameBoard();
                    System.out.println("Player Two wins!");
                    playerOneWinCount++;
                    currentGameWon = true;
                }
            }
        }
    }

    public Boolean checkForHorizontalWin(char player){
        //for first play found, playerToTheLeft will be 'x'
        char playerToTheLeft = 'x';
        int horizontalCount = 0;
        Boolean winner = false;

        for (int row = 6; row >= 0; row--){
            for (int col = 1; col < 15; col += 2) {
                //if color is found in current row
                if(gameBoard[row][col] == player){
                    //if color found is the first or consecutive found
                    if (playerToTheLeft == player || playerToTheLeft == 'x') {
                        horizontalCount++;
                    }
                    //if there are four in a row, winner!
                    if(horizontalCount == 4){
                        winner = true;
                        break;
                    }
                }
                //current find = playerToTheLeft for next iteration
                playerToTheLeft = gameBoard[row][col];
            }
            //if the end of the row is reached without a win, reset count
            horizontalCount = 0;
        }
        //returns true or false
        return winner;
    }

    public Boolean checkForVerticalWin(char player){
        //for first play found, playerRightBeneath will be 'x'
        char playerRightBeneath = 'x';
        int verticalCount = 0;
        Boolean winner = false;

        for (int col = 1; col < 15; col += 2){
            for (int row = 6; row >= 0; row--) {
                //if color is found in current column
                if(gameBoard[row][col] == player){
                    //if color found is the first or consecutive found
                    if(playerRightBeneath == player || playerRightBeneath == 'x')
                    verticalCount++;
                    //if there are four in a row, winner!
                    if(verticalCount == 4){
                        winner = true;
                        break;
                    }
                }
                //current find = playerRightBeneath for next iteration
                playerRightBeneath = gameBoard[row][col];
            }
            //if the end of the column is reached without a win, reset count
            verticalCount = 0;
        }
        //returns true or false
        return winner;
    }

    public Boolean checkForForwardDiagonalWin(char player) {
        //forwards diagonal (/)
        int forwardDiagonalCount = 0;
        char previousPlayerOnDiagonal = 'x';
        int col = 1;
        Boolean winner = false;

        for (int row = 6; row >= 0; row--) {
            if (gameBoard[row][col] == player) {
                //if color found is the first or consecutive found on diagonal
                if (previousPlayerOnDiagonal == player || previousPlayerOnDiagonal == 'x') {
                    forwardDiagonalCount++;
                }
                //if there are four in a row, winner!
                if (forwardDiagonalCount == 4) {
                    winner = true;
                    break;
                }
            }
            //current find = previousPlayerOnDiagonal for next iteration
            previousPlayerOnDiagonal = gameBoard[row][col];
            //column only increments when color is found
            col += 2;
        }
        forwardDiagonalCount = 0;
        return winner;
    }

    public Boolean checkForBackwardsDiagonalWin(char player) {
        //backward diagonal (\)
        int backwardsDiagonalCount = 0;
        char previousPlayerOnDiagonal = 'x';
        int col = 13;
        Boolean winner = false;

        for (int row = 6; row >= 0; row--) {
            if (gameBoard[row][col] == player) {
                //if color found is the first or consecutive found on diagonal
                if (previousPlayerOnDiagonal == player || previousPlayerOnDiagonal == 'x') {
                    backwardsDiagonalCount++;
                }
                //if there are four in a row, winner!
                if (backwardsDiagonalCount == 4) {
                    winner = true;
                    break;
                }
            }
            //current find = previousPlayerOnDiagonal for next iteration
            previousPlayerOnDiagonal = gameBoard[row][col];
            //column only increments when color is found
            col -= 2;
        }
        backwardsDiagonalCount = 0;
        return winner;
    }
}
