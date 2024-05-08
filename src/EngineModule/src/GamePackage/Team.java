package GamePackage;

public class Team {

    private Guesser guesser;
    private Hinter hinter;
    int score,wordsCount ;
    String teamName;

    public Team(Guesser guesser, Hinter hinter) {
        this.guesser = guesser;
        this.hinter = hinter;
        score = 0;
        wordsCount = 0;
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public String toString(){
        return teamName;
    }
}
