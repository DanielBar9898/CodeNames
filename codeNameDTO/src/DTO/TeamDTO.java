package DTO;

import java.util.Set;

public class TeamDTO {
    private String teamName;
    private int wordsToGuess;
    private int numOfDefiners;
    private int numOfRegisteredDefiners;
    private int numOfGuessers;
    private int numOfRegisteredGuessers;

    public TeamDTO(String teamName, int wordsToGuess, int numOfDefiners, int numOfRegisteredDefiners, int numOfGuessers, int numOfRegisteredGuessers) {
        this.teamName = teamName;
        this.wordsToGuess = wordsToGuess;
        this.numOfDefiners = numOfDefiners;
        this.numOfRegisteredDefiners = numOfRegisteredDefiners;
        this.numOfGuessers = numOfGuessers;
        this.numOfRegisteredGuessers = numOfRegisteredGuessers;
    }

    // Getters and Setters
    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }
    public int getWordsToGuess() { return wordsToGuess; }
    public void setWordsToGuess(int wordsToGuess) { this.wordsToGuess = wordsToGuess; }
    public int getNumOfDefiners() { return numOfDefiners; }
    public void setNumOfDefiners(int numOfDefiners) { this.numOfDefiners = numOfDefiners; }
    public int getNumOfRegisteredDefiners() { return numOfRegisteredDefiners; }
    public void setNumOfRegisteredDefiners(int numOfRegisteredDefiners) { this.numOfRegisteredDefiners = numOfRegisteredDefiners; }
    public int getNumOfGuessers() { return numOfGuessers; }
    public void setNumOfGuessers(int numOfGuessers) { this.numOfGuessers = numOfGuessers; }
    public int getNumOfRegisteredGuessers() { return numOfRegisteredGuessers; }
    public void setNumOfRegisteredGuessers(int numOfRegisteredGuessers) { this.numOfRegisteredGuessers = numOfRegisteredGuessers; }
}
