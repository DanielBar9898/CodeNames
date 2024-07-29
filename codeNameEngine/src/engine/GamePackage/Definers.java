package engine.GamePackage;


public class Definers extends Player {
    private String hint;
    private int wordsHint;

    public Definers(String name, Role role, int gameSerialNumber,String teamOfPlayer) {
        super(name, role,gameSerialNumber,teamOfPlayer);
    }



    public void setHint(String otherHint) {
        this.hint = otherHint;
    }

    public void setWordsHint(int wordsHint) {
        this.wordsHint = wordsHint;
    }


    public String getHint() {
        return hint;
    }
    public int getWordsHint() {
        return wordsHint;
    }

    public void printFullBoard(){
        return;
    }
    public void playHinterTurn(String hint,int numOfWords){
        setHint(hint);
        setWordsHint(numOfWords);
    }


}
