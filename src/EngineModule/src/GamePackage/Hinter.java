package GamePackage;

public class Hinter extends Player {
    private Word.Team team;
    private String hint;
    Board fullBoard;

    public Hinter(Word.Team team) {
        this.team = team;
    }

    public Hinter(Hinter otherHinter){
        this.team = otherHinter.team;
        this.hint = otherHinter.hint;
    }

    public void setHint(String otherHint) {
        this.hint = otherHint;
    }

    public String getHint() {
        return this.hint;
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
        return;
    }
}
