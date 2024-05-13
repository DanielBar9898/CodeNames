package GamePackage;

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

    public Word(String word) {
        this.word = word;
        serialNumber = generateSerialNumber();
        found = false;
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

    public int getSerialNumber() {
        return serialNumber;
    }

    // Static method to generate and assign serial number
    private static int generateSerialNumber() {
        return ++serialNumber;
    }

    public void found(){
        this.found = true;
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
}
