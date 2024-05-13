package GamePackage;

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

    public void updateGameByWord(Word word,Team playerTeam){
        if(true){
           System.out.println("Its correct, you have earned your team a point");
            playerTeam.guessedRight();
            word.found();
        }
        else if(true){
            System.out.println("Its wrong, you have earned the other team a point");

        }
        else if(word.getColor() == Word.cardColor.BLACK){
            System.out.println("OMG, its a black word, game over!");

        }
        else{
            word.found();
            System.out.println("its a natural word");
        }
    }
}
