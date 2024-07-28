package DTO;

public class WordDTO {
    private String word;
    private String wordType;
    private boolean found;
    private int serialNumber;

    public WordDTO(String word, String wordType, boolean found, int serialNumber) {
        this.word = word;
        this.wordType = wordType;
        this.found = found;
        this.serialNumber = serialNumber;
    }

    // Getters and Setters
    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }

    public String getWordType() { return wordType; }
    public void setWordType(String wordType) { this.wordType = wordType; }

    public boolean isFound() { return found; }
    public void setFound(boolean found) { this.found = found; }

    public int getSerialNumber() { return serialNumber; }
    public void setSerialNumber(int serialNumber) { this.serialNumber = serialNumber; }
}
