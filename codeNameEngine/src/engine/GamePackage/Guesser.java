package engine.GamePackage;

import java.util.*;


public class Guesser extends Player {

    private Word.cardColor team;
    Board hiddenBoard;

    public Guesser(Word.cardColor team) {
        this.team = team;
    }
    public Guesser() {}

    public Board getHiddenBoard() {
        return hiddenBoard;
    }
    public void printBoard()
    {
        printHiddenBoard();
    }

    public void playTurn() {
        return;
    }

    public void printHiddenBoard(){
        return;
    }

//    public void playGuesserTurn(){
//        boolean keepGuessing = true;
//        Scanner scanner = new Scanner(System.in);
//        team.getHinter().getHint();
//        int wordsToGet = team.getHinter().getWordsHint();
//        while(wordsToGet > 0 && keepGuessing){
//           keepGuessing = getGuess(team);
//           printHiddenBoard();
//           wordsToGet--;
//        }
//    }
     public boolean checkGuess(List<Integer> wordsIndexes){
         return false;
    }


}
