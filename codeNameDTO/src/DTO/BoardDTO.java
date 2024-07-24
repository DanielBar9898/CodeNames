package DTO;

import java.util.List;
import java.util.Set;

public class BoardDTO {
    private int numRows;
    private int numCols;
    private int numOfBlackWords;
    private int numOfTotalWords;
    private Set<WordDTO> words;

    public BoardDTO(int numRows, int numCols, int numOfBlackWords, int numOfTotalWords, Set<WordDTO> words) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.numOfBlackWords = numOfBlackWords;
        this.numOfTotalWords = numOfTotalWords;
        this.words = words;
    }

    // Getters and Setters
    public int getNumRows() { return numRows; }
    public void setNumRows(int numRows) { this.numRows = numRows; }

    public int getNumCols() { return numCols; }
    public void setNumCols(int numCols) { this.numCols = numCols; }

    public int getNumOfBlackWords() { return numOfBlackWords; }
    public void setNumOfBlackWords(int numOfBlackWords) { this.numOfBlackWords = numOfBlackWords; }

    public int getNumOfTotalWords() { return numOfTotalWords; }
    public void setNumOfTotalWords(int numOfTotalWords) { this.numOfTotalWords = numOfTotalWords; }

    public Set<WordDTO> getWords() { return words; }
    public void setWords(Set<WordDTO> words) { this.words = words; }


}
