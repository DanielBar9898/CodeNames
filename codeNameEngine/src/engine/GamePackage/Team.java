package engine.GamePackage;

import engine.JAXBGenerated2.ECNTeam;

import java.util.*;
public class Team {

    private int wordsToGuess,wordsGuessed;
    protected String teamName;
    Set<Word> wordsNeedToGuess;
    Board teamBoard;

    private Set<Guessers> guessers;
    private Set<Definers> definers;
    private int numOfTurns;
    private int numOfGuessers;
    private int numOfDefiners;
    private Player.Role nextRoleToPlay;
   // private int activeGuessers;
    // private int activeDefiners; function get active!

    public Team(ECNTeam otherTeam){
        this.teamName = otherTeam.getName();
        this.wordsToGuess = otherTeam.getCardsCount();
        numOfGuessers = otherTeam.getGuessers();
        numOfDefiners = otherTeam.getDefiners();

        wordsNeedToGuess = new HashSet<>();
        wordsGuessed = 0;
        guessers = new HashSet<>();
        definers = new HashSet<>();
        nextRoleToPlay = Player.Role.DEFINER;
    }

    public Board getTeamBoard() {
        return teamBoard;
    }
    public int getWordsToGuess() {
        return wordsToGuess;
    }
    public Set<Word> getWordsNeedToGuess() {
        return wordsNeedToGuess;
    }
    public String toString(){
        return "a. Team name: " + teamName + "\n" +
                "b. Words to guess: " + wordsToGuess + "\n" +
                "c. Number of guessers: " + numOfGuessers + ", Guessers registered: " + getActiveGuessers() + "\n" +
                "d. Number of definers: " + numOfDefiners + ", Definers registered: " + getActiveDefiners() + "\n\n";
    }

    public void addPlayerToTeam(Player player) {
        if (player.getRole() == Player.Role.DEFINER && getActiveDefiners() < numOfDefiners) {
            Definers definersPlayer = new Definers(player.getName(), player.getRole(), player.getSerialGameNumber(), player.getTeamOfPlayer());
            definers.add(definersPlayer);
        } else if (player.getRole() == Player.Role.GUESSER && getActiveGuessers() < numOfGuessers) {
            Guessers guessersPlayer = new Guessers(player.getName(), player.getRole(), player.getSerialGameNumber(),player.getTeamOfPlayer());
            guessers.add(guessersPlayer);
        } else {
            throw new IllegalArgumentException("Player role is not recognized or team is full");
        }
    }
    public Definers getDefiner() { // the first one!
        if (!definers.isEmpty()) {
            return definers.iterator().next();
        }
        return null;
    }
    public Guessers getGuesser(){
        if (!guessers.isEmpty()) {
            return guessers.iterator().next();
        }
        return null;
}
    public boolean isFull() {
    if (numOfDefiners == getActiveDefiners() && numOfGuessers == getActiveGuessers())
        return true;
    else
        return false;
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


    public int getActiveGuessers(){
        return guessers.size();
    }
    public int getActiveDefiners(){
        return definers.size();
    }

    public int getWordsGuessed() {
        return wordsGuessed;
    }

    public int getNumOfTurns() {
        return numOfTurns;
    }

    public boolean teamMember(String playerName){
     for(Guessers guesser : guessers){
         if(guesser.getName().equals(playerName)){
             return true;
         }
     }
     for(Definers definer : definers){
         if(definer.getName().equals(playerName)){
             return true;
         }
     }
     return false;
    }
    public void switchTurn(){
        if(nextRoleToPlay == Player.Role.GUESSER){
            nextRoleToPlay = Player.Role.DEFINER;
        }
        else {
            nextRoleToPlay = Player.Role.GUESSER;
        }
    }
    public String getNextRoleToPlay(){
        if(nextRoleToPlay == Player.Role.GUESSER){
            return "Guesser";
        }
        return "Definer";
    }
}
