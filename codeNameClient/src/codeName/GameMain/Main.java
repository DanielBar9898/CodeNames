package codeName.GameMain;
import engine.*;
import codeName.*;
import engine.EnginePackage.BooleanWrapper;
import engine.EnginePackage.EngineImpl;
import engine.GamePackage.Game;
import engine.GamePackage.Team;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        int choice, wordsToGuess, lastIndex = 1;
        Team team1 = null, team2 = null;
        boolean team1Turn = true, otherTeamWord = false, gameStarted = false, newGame = true, validFile = false;
        BooleanWrapper gameOver = new BooleanWrapper(false);
        Game currentGame = null;
        String fileName=null, currentHint,numToCheck;
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello, and welcome to Code Names game!");
        EngineImpl engine = new EngineImpl();
        engine.showGameMenu();
        numToCheck= sc.nextLine();
        choice=currentGame.checkChoiceInput(numToCheck);
        while (choice != 6) {
            switch (choice) {
                case 1:
                    System.out.println("Enter XML file path: ");
                    fileName = sc.nextLine();
                    currentGame = engine.loadXmlFile(fileName);
                    while (currentGame == null) {
                        System.out.println("please enter your XML file name:");
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
                        currentGame = engine.loadXmlFile(fileName);
                        gameStarted = true; newGame=true;
                        System.out.println("The game has started! , please choose one of the following:");
                        team1 = currentGame.getTeam1();
                        team2 = currentGame.getTeam2();
                        currentGame.getGameBoard().assignWordsToTeams(team1, team2);
                    }
                    engine.showGameMenu();
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                case 4:
                    boolean HiddenBoard = true;
                    boolean VisibleBoard = false;
                    if(newGame) {
                        team1Turn = true;
                        newGame = false;
                    }
                    if (currentGame == null) {
                        System.out.println("You have not entered a valid XML file!");
                    }
                    else if(!gameStarted|| team1 == null || team2 == null){
                        System.out.println("You have to start a game before playing a turn");
                    }
                    else {
                        if (team1Turn) {                      //         Team 1 turn!!!
                            team1.printTeamTurn();
                            currentGame.getGameBoard().printTheBoard(VisibleBoard);
                            System.out.println("Please enter your hint:");
                            currentHint = sc.nextLine();
                            currentHint = currentGame.checkHintInput(currentHint);
                            System.out.println("How many words should your partner guess?:");
                            numToCheck = sc.nextLine();
                            wordsToGuess= currentGame.checkNumInput(numToCheck);
                            engine.playTurn(team1, currentHint, wordsToGuess);
                            currentGame.getGameBoard().printTheBoard(HiddenBoard);
                            while (lastIndex > 0&&wordsToGuess>0) {
                                currentGame.printInfoAboutTheTurn(currentHint, wordsToGuess);
                                HiddenBoard=true;
                                numToCheck = sc.nextLine();
                                lastIndex= currentGame.checkNumInput(numToCheck);
                                wordsToGuess--;
                                if(lastIndex>0){
                                    if(currentGame.getGameBoard().getWordBySerialNumber(lastIndex).isFound()) {
                                        System.out.println("Someone already guessed the word, please choose another one:");
                                        numToCheck = sc.nextLine();
                                        lastIndex= currentGame.checkNumInput(numToCheck);
                                        wordsToGuess++;
                                    }
                                    otherTeamWord = engine.playTurn(team2, lastIndex,gameOver);

                                    if(gameOver.getValue()) {
                                        return;
                                    }
                                    if (otherTeamWord) {
                                        team2.guessedRight();
                                        otherTeamWord = false;
                                        wordsToGuess=0;
                                    }
                                }
                                if(wordsToGuess>0) {
                                    currentGame.getGameBoard().printTheBoard(HiddenBoard);
                                }
                            }
                            team1Turn = false;
                            lastIndex = 1;
                        }
                        else {                         //         Team 2 turn!!!
                            team2.printTeamTurn();
                            currentGame.getGameBoard().printTheBoard(VisibleBoard);
                            System.out.println("Please enter your hint:");
                            currentHint = sc.nextLine();
                            currentHint = currentGame.checkHintInput(currentHint);
                            System.out.println("How many words should your partner guess?:");
                            numToCheck = sc.nextLine();
                            wordsToGuess= currentGame.checkNumInput(numToCheck);
                            engine.playTurn(team2, currentHint, wordsToGuess);
                            currentGame.getGameBoard().printTheBoard(HiddenBoard);
                            while (lastIndex > 0&&wordsToGuess>0) {
                                currentGame.printInfoAboutTheTurn(currentHint, wordsToGuess);
                                numToCheck = sc.nextLine();
                                lastIndex= currentGame.checkNumInput(numToCheck);
                                wordsToGuess--;
                                if(lastIndex>0){
                                    if(currentGame.getGameBoard().getWordBySerialNumber(lastIndex).isFound()) {
                                        System.out.println("Someone already guessed the word, please choose another one:");
                                        numToCheck = sc.nextLine();
                                        lastIndex= currentGame.checkNumInput(numToCheck);
                                    }
                                    otherTeamWord = engine.playTurn(team2, lastIndex,gameOver);

                                    if(gameOver.getValue()) {
                                        return;
                                    }
                                    if (otherTeamWord) {
                                        team2.guessedRight();
                                        otherTeamWord = false;
                                        wordsToGuess=0;
                                    }
                                }
                                currentGame.getGameBoard().printTheBoard(HiddenBoard);
                            }
                            team1Turn = true;
                            lastIndex = 1;
                        }
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
