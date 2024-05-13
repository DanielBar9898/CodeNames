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
                    while (currentGame == null && !validFile) {
                        System.out.println("please enter your XML file name:");
                        currentGame = engine.loadXmlFile(fileName);
                        validFile = currentGame.validateFile();
                    }
                    team1 = new Team(currentGame.getTeam1());
                    team2 = new Team(currentGame.getTeam2());
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
                    break;

                case 4:
                    if (currentGame == null || team1 == null || team2 == null) {
                        System.out.println("You have not entered a valid XML file!");
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
                            System.out.println("please enter the word index you want to guess:");
                            lastIndex = sc.nextInt();
                            sc.nextLine();
                            while (lastIndex > 0) {
                                otherTeamWord = engine.playTurn(team1, lastIndex);
                                if (otherTeamWord) {
                                    team2.guessedRight();
                                    otherTeamWord = false;
                                }
                                System.out.println("please enter the word index you want to guess:");
                                lastIndex = sc.nextInt();
                                sc.nextLine();
                            }
                            team1Turn = false;
                        }
                        else {
                            team2.printTeamTurn();
                            engine.playTurn(team2, currentHint, wordsToGuess);
                            System.out.println("please enter the word index you want to guess:");
                            lastIndex = sc.nextInt();
                            sc.nextLine();
                            while (lastIndex > 0) {
                                otherTeamWord = engine.playTurn(team2, lastIndex);
                                if (otherTeamWord) {
                                    team1.guessedRight();
                                    otherTeamWord = false;
                                }
                                System.out.println("please enter the word index you want to guess:");
                                lastIndex = sc.nextInt();
                                sc.nextLine();
                            }
                            team1Turn = true;
                        }
                    }
            }
        }
    }
}
