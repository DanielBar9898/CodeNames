package engine.GamePackage;

import java.util.Objects;

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
    private String wordType;

    public Word(String word) {
        this.word = word;
        wordSerialNumber = serialNumber;
        serialNumber++;
    }

    public void setWordType(String wordType){
        this.wordType = wordType;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Word other = (Word) obj;
        return Objects.equals(word, other.word);
    }

    public void checkWord(Word word,Team team){
        return;
    }

    public cardColor getColor() {
        return color;
    }
    public String getWordType(){
        return this.wordType;
    }
    public boolean isFound() {
        return found;
    }

    public String getCharWordType(){
        switch (this.wordType){
            case "Neutral":
                return "N";

            case "Black":
                return "Black";

        }
        return this.wordType;
    }
}
