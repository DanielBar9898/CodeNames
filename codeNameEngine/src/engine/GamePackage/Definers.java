package engine.GamePackage;


public class Definers extends Player {
    private Word.cardColor team;
    private String hint;
    private int wordsHint;

    public Definers(String name, Role role) {
        super(name, role);
    }

    //  public Definers(Word.cardColor team) {
      //  this.team = team;
   // }
   // public Definers(){}

    public void setHint(String otherHint) {
        this.hint = otherHint;
    }

    public void setWordsHint(int wordsHint) {
        this.wordsHint = wordsHint;
    }


    public void getHint() {
        System.out.println("The hint is " + hint +",number of words related is " + wordsHint);
    }


    public void printFullBoard(){
        return;
    }
    public void playHinterTurn(String hint,int numOfWords){
        setHint(hint);
        setWordsHint(numOfWords);
    }


}
