package GamePackage;

import java.util.*;


public class Guesser extends Player {

    private Word.cardColor team;
    Board hiddenBoard;

    public Guesser(Word.cardColor team) {
        this.team = team;
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
     public boolean getGuess(Team playerTeam){
         int guessedWordNumber;
         Word currGuessedWord = null;
         Scanner scanner = new Scanner(System.in);
         System.out.println("Please enter the word number, unless you want to exit then press 0 or negative number:\n");
         guessedWordNumber = scanner.nextInt();
         if(guessedWordNumber <= 0){
             return false;
         }
         currGuessedWord = hiddenBoard.getWordBySerialNumber(guessedWordNumber);
         updateGameByWord(currGuessedWord, playerTeam);
         return true;
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
