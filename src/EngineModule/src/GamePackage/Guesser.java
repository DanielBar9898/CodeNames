package GamePackage;

import java.util.*;


public class Guesser extends Player {

    private Word.cardColor team;

    public Guesser(Word.cardColor team) {
        this.team = team;
    }
    public Guesser() {}

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


}
