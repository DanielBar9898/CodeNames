package GamePackage;

import JAXBGenerated.ECNBoard;
import JAXBGenerated.ECNGame;

import java.util.HashSet;
import java.util.Set;

public class Game {
    public enum TeamName{
        TEAM1,
        TEAM2;
    }
    Team team1;
    Team team2;
    Set<Word> blackWords;
    Set<Word> gameWords;
    Board gameBoard;

    public Game(ECNGame game) {
        gameWords = new HashSet<>();
        blackWords = new HashSet<>();
        String words = game.getECNWords().getECNGameWords();
        String [] s = words.split("\\s");
        String [] sepratedWords = new String[s.length-1];
        System.arraycopy(s, 1, sepratedWords, 0, sepratedWords.length);
        //avoid first spot because its""
        for(String word : sepratedWords) {
            gameWords.add(new Word(word));
        }
        String bWords = game.getECNWords().getECNBlackWords();
        String [] sepratedBlack = bWords.split("\\s");
        String [] sepratedBlackWords = new String[sepratedBlack.length-1];
        System.arraycopy(sepratedBlack, 1, sepratedBlackWords, 0, sepratedBlackWords.length);
        for(String word : sepratedBlackWords) {
            gameWords.add(new Word(word));
            blackWords.add(new Word(word));
        }
        ECNBoard b = game.getECNBoard();
        gameBoard = new Board(b);
        gameBoard.addWordsToBoard(gameWords);
        team1 = new Team(game.getECNTeam1().getName(),game.getECNTeam1().getCardsCount(), Word.cardColor.TEAM1,gameBoard);
        team2 = new Team(game.getECNTeam2().getName(), game.getECNTeam2().getCardsCount(), Word.cardColor.TEAM2,gameBoard);
    }
    public Board getGameBoard() {
        return gameBoard;
    }
    public boolean validateFile(){
        boolean cardsCount,blackCardsCount,sumOfCards,rowsColumns,teamNames;
        cardsCount = this.cardsCount();
        blackCardsCount = this.blackCardsCount();
        sumOfCards = this.sumOfCardsCount();
        rowsColumns = this.rowsColumnsCount();
        teamNames = this.teamNames();
        if(!cardsCount){
            System.out.println("The cards count is not valid");
            return false;
        }
        if(!blackCardsCount){
            System.out.println("The black cards count is not valid");
            return false;
        }
        if(!sumOfCards){
            System.out.println("The sum of the cards count is not valid");
            return false;
        }
        if(!rowsColumns){
            System.out.println("The rows columns count is not valid");
            return false;
        }
        if(!teamNames){
            System.out.println("The teams have the same name!");
            return false;
        }
        return true;
    }

    public boolean cardsCount(){
        return gameBoard.getNumOfWords()<gameWords.size();
    }
    public boolean sumOfCardsCount(){
        return team1.getWordsToGuess()+team2.getWordsToGuess()<gameBoard.getNumOfWords();
    }
    public boolean blackCardsCount(){
        return gameBoard.getNumOfBlackWords()<blackWords.size();
    }
    public boolean rowsColumnsCount(){
        return gameBoard.getNumCols()*gameBoard.getNumRows()>=gameBoard.getNumOfWords();
    }
    public boolean teamNames(){
        return !(team1.getTeamName().equalsIgnoreCase(team2.getTeamName()));
    }

    public Team getTeam1(){
        return team1;
    }
    public Team getTeam2(){
        return team2;
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("1. Number of all words available for choice is ").append(gameWords.size()).append("\n");
        result.append("2. Number of black words available for choice is ").append(blackWords.size()).append("\n");
        result.append("3. In this game there will be ").append(gameBoard.numOfTotalWords-gameBoard.numOfBlackWords).append(" normal words ")
                .append("and ").append(gameBoard.numOfBlackWords).append(" black words\n");
        result.append(team1.toString()).append("\n");
        result.append(team2.toString()).append("\n");
        return result.toString();
    }

}

