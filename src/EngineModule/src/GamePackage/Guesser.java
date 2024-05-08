package GamePackage;

import java.util.List;
import java.util.ArrayList;
public class Guesser extends Player {

    private Word.Team team;
    private List<String> guessedWords;
    Board hiddenBoard;

    public Guesser(Word.Team team) {
        this.team = team;
        guessedWords = new ArrayList<>();
    }

    public Guesser(Guesser otherGuesser){
        this.team = otherGuesser.team;
        this.guessedWords = otherGuesser.guessedWords;
    }

    public void guessWord(String word) {
        guessedWords.add(word);
    }

    public void printBoard()
    {
        printHiddenBoard();
    }

    public void playTurn()
    {
        return;
    }

    public void printHiddenBoard(){
        return;
    }
}
