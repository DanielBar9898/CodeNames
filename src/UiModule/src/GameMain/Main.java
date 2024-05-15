package GameMain;
import EnginePackage.*;
import GamePackage.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        int choice, wordsToGuess, lastIndex = 1;
        boolean validFile = false;
        Team team1 = null, team2 = null;
        boolean team1Turn = true;
        boolean otherTeamWord = false;
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
        while (!gameOver.getValue()) {
            switch (choice) {
                case 1:
                    System.out.println("Enter XML file path: ");
                    fileName = sc.nextLine();
                    currentGame = engine.loadXmlFile(fileName);
                    while (currentGame == null && !validFile) {
                        System.out.println("please enter your XML file name:");
                        currentGame = engine.loadXmlFile(fileName);
                        validFile = currentGame.validateFile();
                    }
                    System.out.println("File successfully loaded!");
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
                    gameStarted = true;
                    System.out.println("The game has started! , please choose one of the following:");
                    team1 = currentGame.getTeam1();
                    team2 = currentGame.getTeam2();
                    currentGame.getGameBoard().assignWordsToTeams(team1, team2);
                    engine.showGameMenu();
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                case 4:
                    if (currentGame == null) {
                        System.out.println("You have not entered a valid XML file!");
                    }
                    else if(!gameStarted|| team1 == null || team2 == null){
                        System.out.println("You have to start a game before playing a turn");
                    }
                    else {
                        System.out.println("Please enter your hint:");
                        currentHint = sc.nextLine();
                        System.out.println("How many words should your partner guess?:");
                        wordsToGuess = sc.nextInt();
                        sc.nextLine();
                        if (team1Turn) {
                            team1.printTeamTurn();
                            engine.playTurn(team1, currentHint, wordsToGuess);
                            while (lastIndex > 0&&wordsToGuess>0) {
                                System.out.println("please enter the word index you want to guess:");
                                lastIndex = sc.nextInt();
                                sc.nextLine();
                                wordsToGuess--;
                                if(lastIndex>0){
                                    otherTeamWord = engine.playTurn(team1, lastIndex,gameOver);
                                    if(gameOver.getValue()) {
                                        choice = 6;
                                        break;
                                    }
                                    if (otherTeamWord) {
                                        team2.guessedRight();
                                        otherTeamWord = false;
                                    }
                                }
                            }
                            team1Turn = false;
                        }
                        else {
                            team2.printTeamTurn();
                            engine.playTurn(team2, currentHint, wordsToGuess);
                            while (lastIndex > 0&&wordsToGuess>0) {
                                System.out.println("please enter the word index you want to guess:");
                                lastIndex = sc.nextInt();
                                sc.nextLine();
                                wordsToGuess--;
                                if (lastIndex > 0) {
                                    otherTeamWord = engine.playTurn(team2, lastIndex, gameOver);
                                    if (gameOver.getValue()) {
                                        choice = 6;
                                        break;
                                    }
                                    if (otherTeamWord) {
                                        team1.guessedRight();
                                        otherTeamWord = false;
                                    }

                                }
                            }
                            team1Turn = true;
                        }
                        System.out.println("The turn has ended!");
                        lastIndex = 1;
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
                case 6:
                    gameOver.setValue(true);
                    System.out.println("Game over! BYE BYE");
                    break;
                default:
                    System.out.println("Invalid choice!");

            }
        }
    }
}
