package engine.GamePackage;

import java.util.*;


public class Guessers extends Player {

    private Word.cardColor team;
    Board hiddenBoard;

    public Guessers(String name, Role role,int gameSerialNumber) {
        super(name, role, gameSerialNumber);
    }

    //public Guessers(Word.cardColor team) {
     //   this.team = team;
  //  }
   // public Guessers() {}

    public Board getHiddenBoard() {
        return hiddenBoard;
    }

     public boolean checkGuess(List<Integer> wordsIndexes){
         return false;
    }


}