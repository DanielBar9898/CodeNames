package engine.GamePackage;

public class Word {
    public enum cardColor{
        TEAM1,
        TEAM2,
        NEUTRAL,
        BLACK;
    }
    private  cardColor color;
    private String word;
    private boolean found ;
    private static int serialNumber = 1;
    private int wordSerialNumber;
    private String teamWord;

    public Word(String word) {
        this.word = word;
        wordSerialNumber = serialNumber;
        serialNumber++;
    }

    public void setColor(Word.cardColor cardColor){
        switch (cardColor){
            case TEAM1:
                this.color = cardColor.TEAM1;
                break;
            case TEAM2:
                this.color = cardColor.TEAM2;
                break;
            case NEUTRAL:
                this.color = cardColor.NEUTRAL;
                break;
            case BLACK:
                this.color = cardColor.BLACK;
                break;
        }
    }

    public void setTeamWord(String teamWord){
        this.teamWord = teamWord;
    }
    public int getSerialNumber() {
        return wordSerialNumber;
    }

    public void setSerialNumber(int wordSerialNumber) {
        this.wordSerialNumber = wordSerialNumber;
    }
    public void found(){
        this.found = true;
    }

    public String getCharFound() {
        if(found){
            return "V";
        }
        else
            return "X";
    }

    public String toString(){
        return word;
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }

    public boolean equals(Word word){
        return this.word.equals(word.word);
    }

    public void checkWord(Word word,Team team){
        return;
    }

    public cardColor getColor() {
        return color;
    }
    public boolean isFound() {
        return found;
    }
    public String getCharColor(){
        switch (this.color){
            case TEAM1:
                return "T1";

            case TEAM2:
                return "T2";

            case NEUTRAL:
                return "N";

            case BLACK:
                return "Black";

        }
        return null;
    }
}
