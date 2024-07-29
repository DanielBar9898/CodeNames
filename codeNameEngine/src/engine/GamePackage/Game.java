package engine.GamePackage;

import engine.JAXBGenerated2.*;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Game {
    private String name;
    private Set<Word> blackWords;
    private Set<Word> gameWords;
    private Board gameBoard;
    private boolean active;
    private String dictName;
    private ArrayList<Team> teams;
    private static int nextSerialNumber = 1;
    private int gameSerialNumber;
    private Team currentTeam;
    private String fileName;

    public Game(ECNGame game) {
        this.gameSerialNumber = nextSerialNumber++;
        int blackCards = game.getECNBoard().getBlackCardsCount();
        int cards = game.getECNBoard().getCardsCount();
        gameWords = new HashSet<>(blackCards);
        blackWords = new HashSet<>(cards);
        teams = new ArrayList<>();
        List<ECNTeam> ecnTeamList = game.getECNTeams().getECNTeam();
        for (ECNTeam e : ecnTeamList) {
            teams.add(new Team(e));
        }
        gameBoard = new Board(game.getECNBoard());
        setDictName(game.getECNDictionaryFile());
        active = false;
        currentTeam = teams.get(0);
        name = game.getName();
    }
    public int getNumOfWordsInSingleGame(){
        return gameBoard.getNumofTotalWords();
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public void checkAndActivateGame() {
        for (Team team : teams) {
            if (!team.isFull()) {
                active = false;
                return;
            }
        }
        active = true;
    }

    public synchronized void extractWordsFromFile(File file) throws IOException {
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
        gameBoard.addWordsToBoard(gameWords);
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public String getName() {
        return name;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public synchronized boolean validateFile(PrintWriter out) {
        boolean cardsCount, blackCardsCount, sumOfCards, rowsColumns, teamNames, guessersDefiners;
        cardsCount = this.cardsCount();
        blackCardsCount = this.blackCardsCount();
        sumOfCards = this.sumOfCardsCount();
        rowsColumns = this.rowsColumnsCount();
        teamNames = this.teamNames();
        guessersDefiners = this.definersGuessers();
        if (!cardsCount) {
            out.println("The cards count is not valid");
            return false;
        }
        if (!blackCardsCount) {
            out.println("The black cards count is not valid");
            return false;
        }
        if (!sumOfCards) {
            out.println("The sum of the cards count is not valid");
            return false;
        }
        if (!rowsColumns) {
            out.println("The rows columns count is not valid");
            return false;
        }
        if (!teamNames) {
            out.println("The teams have the same name!");
            return false;
        }
        if (!guessersDefiners) {
            out.println("Not enough guessers/definers defined");
            return false;
        }
        return true;
    }

    public boolean definersGuessers() {
        for (Team t : teams) {
            if (t.getNumOfGuessers() <= 0 || t.getNumOfDefiners() <= 0) {
                return false;
            }
        }
        return true;
    }

    public boolean cardsCount() {
        return gameBoard.getNumOfWords() < gameWords.size();
    }

    public boolean sumOfCardsCount() {
        int num = 0;
        for (Team team : teams) {
            num += team.getWordsToGuess();
        }
        return num < gameBoard.getNumOfWords();
    }

    public boolean blackCardsCount() {
        return gameBoard.getNumOfBlackWords() < gameWords.size();
    }

    public boolean rowsColumnsCount() {
        return gameBoard.getNumCols() * gameBoard.getNumRows() >= gameBoard.getNumOfWords();
    }

    public boolean teamNames() {
        Set<String> names = new HashSet<>();
        for (Team team : teams) {
            if (!names.add(team.getTeamName())) {
                return false; // Duplicate name found
            }
        }
        return true; // All names are unique
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void printInfoAboutTheTurn(String currentHint, int wordsToGuess) {
        System.out.println("\n**If you want to stop guessing press 0 or negative number");
        System.out.println("Remember: The Hint is: *" + currentHint + "*, Number of words remain to guess:" + wordsToGuess);
        System.out.println("Please enter the word index you want to guess:");
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        //result.append("Game number #"+gameNumber);
        result.append("1.Game name : " + name + "\n");
        result.append("2.Game status is " + (active ? "ACTIVE" : "PENDING") + "\n");
        result.append("3.Number of rows X columns is : " + gameBoard.getNumRows() + "X" + gameBoard.getNumCols() + "\n");
        result.append("4.The name of the dictionary file is: " + dictName);
        result.append("and the number of all words available for choice is ").append(gameWords.size()).append("\n");
        result.append("5. In this game there will be ").append(gameBoard.numOfRegularWords - gameBoard.numOfBlackWords).append(" normal words ")
                .append("and ").append(gameBoard.numOfBlackWords).append(" black words\n");
        result.append("6.There is " + teams.size() + " teams, here are their information:\n");
        for (Team t : teams) {
            result.append(t.toString());
        }
        return result.toString();
    }

    public static boolean isValidHintInput(String input) {
        // Regular expression to match only alphabetic characters (one word)
        return input.matches("^[a-zA-Z]+( [a-zA-Z]+)?$");
    }

    public Team getNextTeam() {
        int currentIndex = teams.indexOf(currentTeam);
        int nextIndex = (currentIndex + 1) % teams.size();
        return teams.get(nextIndex);
    }

    public void nextTurn() {
        currentTeam = getNextTeam();
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

                System.out.println("Invalid input. Please enter a number between 0 and " + gameBoard.getWords().size() + ":");
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

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getGameSerialNumber() {
        return gameSerialNumber;
    }


    public int getBlackWordsCount() {
        return blackWords.size();
    }

    public int getGameWordsCount() {
        return gameWords.size();
    }

    public String getDictName() {
        return dictName;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public void setBlackWords() {
        blackWords = gameBoard.getBlackWords();
    }

    public int getWordsSize(){
        return gameBoard.getWords().size();
    }
}
