package GamePackage;

import java.util.Scanner;

public class Hinter extends Player {
    private Word.cardColor team;
    private String hint;
    private int wordsHint;
    Board fullBoard;

    public Hinter(Word.cardColor team) {
        this.team = team;
    }

    public Hinter(Hinter otherHinter){
        this.team = otherHinter.team;
        this.hint = otherHinter.hint;
    }

    public void setHint(String otherHint) {
        this.hint = otherHint;
    }

    public void setWordsHint(int wordsHint) {
        this.wordsHint = wordsHint;
    }

    public int getWordsHint() {
        return wordsHint;
    }

    public void getHint() {
        System.out.println("The hint is " + hint +",number of words related is " + wordsHint);
    }

    public void printBoard()
    {
        printFullBoard();
    }

    public void playTurn()
    {
        playHinterTurn();
    }

    public void printFullBoard(){
        return;
    }
    public void playHinterTurn(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your hint:\n");
        String hint = sc.nextLine();
        System.out.println("How many words should your partner guess?:\n");
        int numOfWords = sc.nextInt();
        setHint(hint);
        setWordsHint(numOfWords);
    }
}
