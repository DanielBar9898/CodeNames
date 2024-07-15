package engine.GamePackage;

import engine.JAXBGenerated.ECNBoard;
import engine.JAXBGenerated.ECNGame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Game {
    public enum TeamName {
        TEAM1,
        TEAM2;
    }
    private String name;
    Team team1;
    Team team2;
    Set<Word> blackWords;
    Set<Word> gameWords;
    Board gameBoard;

    public Game(ECNGame game) {
        gameWords = new HashSet<>();
        blackWords = new HashSet<>();
        String words = game.getECNWords().getECNGameWords();
        String[] s = words.split("\\s");
        String[] sepratedWords = new String[s.length - 1];
        System.arraycopy(s, 1, sepratedWords, 0, sepratedWords.length);
        //avoid first spot because its""
        for (String word : sepratedWords) {
            gameWords.add(new Word(word));
        }
        String bWords = game.getECNWords().getECNBlackWords();
        String[] sepratedBlack = bWords.split("\\s");
        String[] sepratedBlackWords = new String[sepratedBlack.length - 1];
        System.arraycopy(sepratedBlack, 1, sepratedBlackWords, 0, sepratedBlackWords.length);
        for (String word : sepratedBlackWords) {
            gameWords.add(new Word(word));
            blackWords.add(new Word(word));
        }
        ECNBoard b = game.getECNBoard();
        gameBoard = new Board(b);
        gameBoard.addWordsToBoard(gameWords);
        team1 = new Team(game.getECNTeam1().getName(), game.getECNTeam1().getCardsCount(), Word.cardColor.TEAM1, gameBoard);
        team2 = new Team(game.getECNTeam2().getName(), game.getECNTeam2().getCardsCount(), Word.cardColor.TEAM2, gameBoard);
    }

    public void extractWordsFromFile(File file) throws IOException {
        Set<Word> words = new HashSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");

        while ((line = reader.readLine()) != null) {
            // Split the line into words using whitespace as the delimiter
            String[] lineWords = line.split("\\s+");
            for (String word : lineWords) {
                // Remove punctuation and trim whitespace
                word = pattern.matcher(word).replaceAll("").trim();
                if (!word.isEmpty()) {
                    words.add(new Word(word.toLowerCase()));
                }
            }
        }
        reader.close();
        gameWords = words;
    }

    public Board getGameBoard() {
        return gameBoard;
    }
    public String getName() {
        return name;
    }
    public boolean validateFile() {
        boolean cardsCount, blackCardsCount, sumOfCards, rowsColumns, teamNames;
        cardsCount = this.cardsCount();
        blackCardsCount = this.blackCardsCount();
        sumOfCards = this.sumOfCardsCount();
        rowsColumns = this.rowsColumnsCount();
        teamNames = this.teamNames();
        if (!cardsCount) {
            System.out.println("The cards count is not valid");
            return false;
        }
        if (!blackCardsCount) {
            System.out.println("The black cards count is not valid");
            return false;
        }
        if (!sumOfCards) {
            System.out.println("The sum of the cards count is not valid");
            return false;
        }
        if (!rowsColumns) {
            System.out.println("The rows columns count is not valid");
            return false;
        }
        if (!teamNames) {
            System.out.println("The teams have the same name!");
            return false;
        }
        return true;
    }

    public boolean cardsCount() {
        return gameBoard.getNumOfWords() < gameWords.size();
    }

    public boolean sumOfCardsCount() {
        return team1.getWordsToGuess() + team2.getWordsToGuess() < gameBoard.getNumOfWords();
    }

    public boolean blackCardsCount() {
        return gameBoard.getNumOfBlackWords() < blackWords.size();
    }

    public boolean rowsColumnsCount() {
        return gameBoard.getNumCols() * gameBoard.getNumRows() >= gameBoard.getNumOfWords();
    }

    public boolean teamNames() {
        return !(team1.getTeamName().equalsIgnoreCase(team2.getTeamName()));
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void printInfoAboutTheTurn(String currentHint, int wordsToGuess) {
        System.out.println("\n**If you want to stop guessing press 0 or negative number");
        System.out.println("Remember: The Hint is: *" + currentHint + "*, Number of words remain to guess:" + wordsToGuess);
        System.out.println("Please enter the word index you want to guess:");
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("1. Number of all words available for choice is ").append(gameWords.size()).append("\n");
        result.append("2. Number of black words available for choice is ").append(blackWords.size()).append("\n");
        result.append("3. In this game there will be ").append(gameBoard.numOfTotalWords - gameBoard.numOfBlackWords).append(" normal words ")
                .append("and ").append(gameBoard.numOfBlackWords).append(" black words\n");
        result.append(team1.toString()).append("\n");
        result.append(team2.toString()).append("\n");
        return result.toString();
    }

    public static boolean isValidHintInput(String input) {
        // Regular expression to match only alphabetic characters (one word)
        return input.matches("^[a-zA-Z]+( [a-zA-Z]+)?$");
    }



    public static String checkHintInput(String hint) {
        boolean valid = true;
        Scanner sc = new Scanner(System.in);
        while (valid) {
            // Check if the input is valid
            if (isValidHintInput(hint)) {
                valid = false;
                // Proceed with the logic using the valid input
            } else {
                System.out.println("Invalid input. Please enter one or two words without numbers or special characters.");
                // You can prompt the user again or handle the invalid input appropriately
                hint = sc.nextLine();
            }
        }
     return hint;
    }
    public boolean isValidNumInput(String input) {
        try {
            int number = Integer.parseInt(input);
            return number >= 0 && number <= gameBoard.getWords().size();
        } catch (NumberFormatException e) {
            return false; // If input is not a valid integer
        }

    }
    public int checkNumInput(String numOfWords) {
        boolean valid = true;
        Scanner sc = new Scanner(System.in);
        while (valid) {
            // Check if the input is valid
            if (isValidNumInput(numOfWords)) {
                valid = false;
                // Proceed with the logic using the valid input
            } else {

                System.out.println("Invalid input. Please enter a number between 0 and "+ gameBoard.getWords().size()+":");
                // You can prompt the user again or handle the invalid input appropriately
                numOfWords = sc.nextLine();
            }
        }
        return Integer.parseInt(numOfWords);
    }
    public static boolean isValidChoiceInput(String input) {
        try {
            int number = Integer.parseInt(input);
            return number >= 1 && number <= 6;
        } catch (NumberFormatException e) {
            return false; // If input is not a valid integer
        }

    }
    public static int checkChoiceInput(String choice) {
        boolean valid = true;
        Scanner sc = new Scanner(System.in);
        while (valid) {
            // Check if the input is valid
            if (isValidChoiceInput(choice)) {
                valid = false;
                // Proceed with the logic using the valid input
            } else {
                System.out.println("Invalid input. Please enter a number between 1-6.");
                // You can prompt the user again or handle the invalid input appropriately
                choice = sc.nextLine();
            }
        }
        return Integer.parseInt(choice);
    }

}

