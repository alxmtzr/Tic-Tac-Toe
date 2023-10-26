import java.sql.SQLOutput;
import java.util.Scanner;
// TODO: program computer
public class Game {
    private final int GAME_SIZE;
    private Scanner scanner;
    // mode 1 PvP, mode 2 player vs computer
    private int mode;
    private int difficulty;
    private Player player1;
    private Player player2;
    private boolean game_ended = false;
    private boolean won = false;
    private Board board;

    public Game(int gamesize){
        welcomeScreen();
        GAME_SIZE = gamesize;
        scanner = new Scanner(System.in);
        startGame();
    }

    private void startGame() {
        // retrieve game mode from user
        mode = getGameMode();
        // create game board
        board = new Board(GAME_SIZE);


        // creating players
        if(mode == 1){
            // PvP
            player1 = new Player();
            player2 = new Player();
            // player1 with first turn
            player1.setActive(true);
            player2.setActive(false);

            player1.setSign("x");
            player2.setSign("o");

            startGamePvP();
        }else {
            difficulty = getDifficultyLevel();
            if(difficulty == 1){
                startPvCEasy();
            }/*else if(difficulty == 2){
                startPvCHard();
            }*/
        }
    }

    private void startPvCEasy() {
        // TODO: Player vs Computer functionality easy -> computer pics random numbers
    }

    private int getDifficultyLevel() {
        clearScreen();

        // getting difficulty level
        System.out.println("Select difficulty level (1=easy, 2=hard): ");
        int difficulty = scanner.nextInt();

        if(difficulty == 1){
            clearScreen();
            System.out.println("You selected difficulty level --easy--");
            startCountdown(3);
        }else if(difficulty == 2){
            clearScreen();
            System.out.println("You selected difficulty level --hard--");
            startCountdown(3);
        }else {
            clearScreen();
            System.out.println("Invalid input! Please type in correct level.");
            sleep(2000);
            getDifficultyLevel();
        }
        return difficulty;
    }

    private void welcomeScreen() {
        clearScreen();
        System.out.println("----------------- WELCOME TO TIC TAC TOE -----------------");

        try{
            Thread.sleep(2000);
        }catch (InterruptedException interruptedException){
            interruptedException.printStackTrace();
        }
    }

    private void startGamePvP() {
        scanner.nextLine();
        while(!game_ended){
            // clear screen in console
            clearScreen();
            if (player1.isActive()){
                System.out.println("Player 1: Set your 'x' mark at available numbers -> \n");
                board.printBoard();
                String mark = scanner.nextLine();
                setMark(mark);
                // check if three in a row
                threeInARow();
            }else if (player2.isActive()){
                System.out.println("Player 2: Set your 'o' mark at available numbers -> \n");
                board.printBoard();
                String mark = scanner.nextLine();
                setMark(mark);
                // check if three in a row
                threeInARow();
            }
            switchActiveStatus();
            emptyFieldCheck();
        }

        // print winner or draw
        // switch status again because there was no turn
        if (won) {
            switchActiveStatus();
            if (player1.isActive()) {
                clearScreen();
                board.printBoard();
                System.out.println();
                System.out.println("----------- WINNER: PLAYER 1 -----------");
                sleep(3000);
            } else if (player2.isActive()) {
                clearScreen();
                board.printBoard();
                System.out.println();
                System.out.println("----------- WINNER: PLAYER 2 -----------");
                sleep(3000);
            }
        }else {
            clearScreen();
            board.printBoard();
            System.out.println();
            System.out.println("----------- DRAW -----------");
        }
        playAgain();
    }

    private void playAgain() {
        System.out.println("Play again (y/n)?");
        String result = scanner.nextLine();

        if (result.equals("y")) {
            // refresh variables
            refreshVars();
            startGame();
        } else if (result.equals("n")) {
            System.exit(0);
        }else{
            playAgain();
        }
    }

    private void refreshVars() {
        mode = 0;
        game_ended = false;
        won = false;
    }

    private void emptyFieldCheck() {
        boolean availableFields = false;
        for (int i = 0; i < board.getField_array().length; i++) {
            for (int j = 0; j < board.getField_array()[0].length; j++) {
                if (board.getField_array()[i][j].matches("[0-9]")){
                    availableFields = true;
                }
            }
        }

        if (!availableFields){
            game_ended = true;
        }
    }

    private void sleep(int time) {
        try{
            Thread.sleep(time);
        }catch (InterruptedException ire){
            ire.printStackTrace();
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    private int getGameMode() {
        System.out.println("Set game mode 1 or 2 (quit with 3): ");
        System.out.println("1 = Player vs Player");
        System.out.println("2 = Player vs. Computer");

        int input_mode = scanner.nextInt();
        if (input_mode == 1 || input_mode == 2) {
            if (input_mode == 1) {
                System.out.println("Selected mode: player vs player");
                // Countdown 3 seconds
                startCountdown(3);
            } else {
                System.out.println("Selected mode: player vs computer");
                sleep(2000);
            }
            return input_mode;
        } else if (input_mode == 3) {
            System.out.println("quit game");
            System.exit(0);
        } else {
            System.out.println("Invalid input!");
            getGameMode();
        }

        return 0;
    }

    private void startCountdown(int seconds) {
        System.out.println("Game starts in...");
        for (int i = seconds; i > 0; i--) {
            System.out.print("\r" + i);
            sleep(1000);
        }
    }

    private void switchActiveStatus(){
        if (player1.isActive()) {
            player1.setActive(false);
            player2.setActive(true);
        }else{
            player1.setActive(true);
            player2.setActive(false);
        }

    }

    private void setMark(String mark) {
        // set sign x or o
        String sign;
        if (player1.isActive()){
            sign = player1.getSign();
        }else {
            sign = player2.getSign();
        }

        // check which field has been chosen
        boolean setMarkSuccess = false;
        for (int i = 0; i < board.getField_array().length; i++) {
            for (int j = 0; j < board.getField_array()[0].length; j++) {
                if (board.getField_array()[i][j].equals(mark)){
                    board.getField_array()[i][j] = sign;
                    setMarkSuccess = true;
                }
            }
        }

        // if setting mark was not successful
        if(!setMarkSuccess){
            clearScreen();
            System.out.println("Field is already been used. Try again!");
            try{
                Thread.sleep(3000);
            }catch (InterruptedException rte){
                rte.printStackTrace();
            }
            // switch turn again
            switchActiveStatus();
        }
    }

    // game logic

    public boolean threeInARow(){
        // check each row
        checkRows();
        // check each column
        checkColumns();
        // check diagonal rows
        checkDiagonalRows();

        // if there are three in a row
        if(checkRows() || checkColumns() || checkDiagonalRows()){
            game_ended = true;
            won = true;
            return true;
        }else {
            return false;
        }
    }
    private boolean checkDiagonalRows() {
        boolean threeInADiag = false;
        // check both diagonal rows
        if (board.getField_array()[0][0].equals(board.getField_array()[1][1]) &&
        board.getField_array()[0][0].equals(board.getField_array()[2][2])){
            threeInADiag = true;
        }else if (board.getField_array()[0][2].equals(board.getField_array()[1][1]) &&
        board.getField_array()[0][2].equals(board.getField_array()[2][0])){
            threeInADiag = true;
        }
        return threeInADiag;
    }
    private boolean checkColumns() {
        boolean threeInACol = false;
        for (int i = 0; i < board.getField_array()[0].length; i++) {
            // check if entries in row are all equal
            if (board.getField_array()[0][i].equals(board.getField_array()[1][i]) &&
                    board.getField_array()[0][i].equals(board.getField_array()[2][i])) {
                threeInACol = true;
            }
        }

        return threeInACol;
    }
    private boolean checkRows() {
        boolean threeInARowHor = false;
        for (int i = 0; i < board.getField_array().length; i++) {
            // check if entries in row are all equal
            if (board.getField_array()[i][0].equals(board.getField_array()[i][1]) &&
                    board.getField_array()[i][0].equals(board.getField_array()[i][2])) {
                threeInARowHor = true;
            }
        }
        return threeInARowHor;
    }
}
