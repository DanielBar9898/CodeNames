package GamePackage;

public class Word {
    public enum Team{
        TEAM1,
        TEAM2,
        NEUTRAL,
        BLACK;
    }
    private final Team team;
    private String word;
    private boolean found = false;
    private static int serialNumber = 0;

    public Word(Team team, String word) {
        this.team = team;
        this.word = word;
        serialNumber = generateSerialNumber();
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
}
