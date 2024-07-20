package engine.GamePackage;

import engine.JAXBGenerated2.ECNTeam;

import java.util.*;
public class Team {

    private Guesser guesser;
    private Hinter hinter;
    private int wordsToGuess,wordsGuessed;
    protected String teamName;
    Set<Word> wordsNeedToGuess;
    Board teamBoard;
    private int numOfTurns;
    private int numOfGuessers;
    private int numOfDefiners;
    private int activeGuessers;
    private int activeDefiners;

    public Team(ECNTeam otherTeam){
        this.teamName = otherTeam.getName();
        this.wordsToGuess = otherTeam.getCardsCount();
        numOfGuessers = otherTeam.getGuessers();
        numOfDefiners = otherTeam.getDefiners();
        activeDefiners = 0;
        activeGuessers = 0;
    }
    public void playedTurn(){
        numOfTurns++;
    }
    public int getNumOfTurns(){
        return numOfTurns;
    }
    public Board getTeamBoard() {
        return teamBoard;
    }
    public String howManyWordsGuessed(){
        String s = new String("So far the team guessed correctly "+wordsGuessed+"/"+wordsToGuess+" words");
        return s;
    }
    public int getWordsToGuess() {
        return wordsToGuess;
    }
    public Set<Word> getWordsNeedToGuess() {
        return wordsNeedToGuess;
    }
    public String toString(){
        return "a.Team name: "+teamName.toString()+"\nb.Words to guess: "+wordsToGuess + "c.Number of guessers: "+numOfGuessers + "d.Number of definers: "+numOfDefiners+"\n\n";
    }

    public void showTeamWordsState(){
        System.out.println("So far the team guessed correctly "+wordsGuessed+"/"+wordsToGuess+" words");
    }

    public Guesser getGuesser() {
        return guesser;
    }

    public Hinter getHinter() {
        return hinter;
    }

    public void printTeamTurn(){
        System.out.println("Its "+teamName.toString()+" turn");
    }

    public void addWordToGuess(Word word){
        wordsNeedToGuess.add(word);
    }

    public void guessedRight(){
        wordsGuessed++;
    }

    public String getTeamName(){
        return teamName;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return teamName.equals(team.teamName);
    }

    @Override
    public int hashCode() {
        return teamName.hashCode();
    }

    public int getNumOfGuessers(){
        return numOfGuessers;
    }

    public int getNumOfDefiners(){
        return numOfDefiners;
    }

    public int getWordsGuessed(){
        return wordsGuessed;
    }

}
