package GameMain;
import EnginePackage.*;
import GamePackage.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        int choice, wordsToGuess, lastIndex = 1;
        boolean validFile;
        Team team1 = null, team2 = null;
        boolean team1Turn = true;
        boolean otherTeamWord ;
        boolean gameStarted = false;
        BooleanWrapper gameOver = new BooleanWrapper(false);
        Game currentGame = null;
        String fileName, currentHint;
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello, and welcome to Code Names game!");
        EngineImpl engine = new EngineImpl();
        engine.showGameMenu();
        choice = sc.nextInt();
        sc.nextLine();
        while (choice != 6) {
            switch (choice) {
                case 1:
                    System.out.println("Enter XML file path: ");
                    fileName = sc.nextLine();
                    currentGame = engine.loadXmlFile(fileName);
                    while (currentGame == null) {
                        System.out.println("please enter your XML file name:");
                        fileName = sc.nextLine();
                        currentGame = engine.loadXmlFile(fileName);
                    }
                    validFile = currentGame.validateFile();
                    if(!validFile){
                        System.out.println("The game you loaded is invalid!");
                        currentGame = null;
                    }
                    else{
                        System.out.println("File successfully loaded!");
                    }
                    engine.showGameMenu();
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                case 2:
                    if (currentGame == null) {
                        System.out.println("You have not entered a valid XML file!");
                    } else {
                        engine.showLoadedGameInfo(currentGame);
                    }
                    engine.showGameMenu();
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                case 3:
                    if (currentGame == null) {
                        System.out.println("You have not entered a valid XML file!");
                    }
                    else{
                        gameStarted = true;
                        System.out.println("The game has started! , please choose one of the following:");
                        team1 = currentGame.getTeam1();
                        team2 = currentGame.getTeam2();
                        engine.startGame(currentGame);
                    }
                    engine.showGameMenu();
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                case 4:
                    boolean HiddenBoard = true;
                    boolean VisibleBoard = false;
                    if (currentGame == null) {
                        System.out.println("You have not entered a valid XML file!");
                    }
                    else if(!gameStarted){
                        System.out.println("You have to start a game before playing a turn");
                    }
                    else {
                        if (team1Turn) {
                            team1.playedTurn();
                            team1.printTeamTurn();
                            currentGame.getGameBoard().printTheBoard(VisibleBoard);
                            System.out.println("Please enter your hint:");
                            currentHint = sc.nextLine();
                            System.out.println("How many words should your partner guess?:");
                            while (true) {
                                System.out.print("Enter a number: ");

                                if (sc.hasNextInt()) {
                                    wordsToGuess = sc.nextInt();
                                    break; // Break out of the loop if input is valid
                                } else {
                                    System.out.println("Input is not an integer. Try again.");
                                    sc.nextLine(); // Consume invalid input
                                }
                            }
                            sc.nextLine();
                            engine.playTurn(team1, currentHint, wordsToGuess);
                            currentGame.getGameBoard().printTheBoard(HiddenBoard);
                            while (lastIndex > 0&&wordsToGuess>0) {
                                HiddenBoard=true;
                                System.out.println("please enter the word index you want to guess:\nif you want to stop guessing press 0 or negative number");
                                lastIndex = sc.nextInt();
                                sc.nextLine();
                                wordsToGuess--;
                                if(lastIndex>0){
                                    otherTeamWord = engine.playTurn(team1, lastIndex,gameOver);
                                    if(gameOver.getValue()) {
                                        return;
                                    }
                                    if (otherTeamWord) {
                                        team2.guessedRight();
                                        otherTeamWord = false;
                                    }
                                }
                            }
                            team1Turn = false;
                            lastIndex = 1;
                        }
                        else {
                            team2.playedTurn();
                            team2.printTeamTurn();
                            currentGame.getGameBoard().printTheBoard(VisibleBoard);
                            System.out.println("Please enter your hint:");
                            currentHint = sc.nextLine();
                            System.out.println("How many words should your partner guess?:");
                            while (true) {
                                System.out.print("Enter a number: ");

                                if (sc.hasNextInt()) {
                                    wordsToGuess = sc.nextInt();
                                    break; // Break out of the loop if input is valid
                                } else {
                                    System.out.println("Input is not an integer. Try again.");
                                    sc.nextLine(); // Consume invalid input
                                }
                            }
                            sc.nextLine();
                            currentGame.getGameBoard().printTheBoard(HiddenBoard);
                            engine.playTurn(team2, currentHint, wordsToGuess);
                            while (lastIndex > 0&&wordsToGuess>0) {
                                System.out.println("please enter the word index you want to guess:\nif you want to stop guessing press 0 or negative number");
                                lastIndex = sc.nextInt();
                                sc.nextLine();
                                wordsToGuess--;
                                if(lastIndex>0){
                                    otherTeamWord = engine.playTurn(team2, lastIndex,gameOver);
                                    if(gameOver.getValue()) {
                                        return;
                                    }
                                    if (otherTeamWord) {
                                        team1.guessedRight();
                                        otherTeamWord = false;
                                    }
                                }
                            }
                            team1Turn = true;
                            lastIndex = 1;
                        }try {Thread.sleep(2000); // Sleep for 2 seconds
                        } catch (InterruptedException e) {
                            // Handle InterruptedException if needed
                            e.printStackTrace();}
                        System.out.println("Here's the board for now :");
                        currentGame.getGameBoard().printTheBoard(HiddenBoard);
                        if(gameOver.getValue()) {
                            System.out.println("Game Over!");
                        }else {
                        System.out.println("The turn has ended!");}
                    }
                    engine.showGameMenu();
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                case 5:
                    if(currentGame == null) {
                        System.out.println("You have not entered a valid XML file!");
                    }
                    else if(!gameStarted|| team1 == null || team2 == null){
                        System.out.println("You have to start a game before showing game stats");
                    }
                    else{
                        engine.printGameStats(currentGame,team1Turn);
                    }
                    engine.showGameMenu();
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
            }
        }
    }
}
