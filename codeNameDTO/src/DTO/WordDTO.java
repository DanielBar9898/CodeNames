package DTO;

public class WordDTO {
    private String word;
    private String color;
    private boolean found;
    private int serialNumber;

    public WordDTO(String word, String color, boolean found, int serialNumber) {
        this.word = word;
        this.color = color;
        this.found = found;
        this.serialNumber = serialNumber;
    }

    // Getters and Setters
    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public boolean isFound() { return found; }
    public void setFound(boolean found) { this.found = found; }

    public int getSerialNumber() { return serialNumber; }
    public void setSerialNumber(int serialNumber) { this.serialNumber = serialNumber; }
}
