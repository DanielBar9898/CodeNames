package GamePackage;

import java.util.HashSet;
import java.util.Set;

public class Board {

    private Set<Word> words;
    int numRows;
    int numCols;

    public Board(int rows,int col) {
        words = new HashSet<>();
        numRows = rows;
        numCols = col;
    }

    public void addWord(Word word) {
        words.add(word);
    }

    public Set<Word> getWords() {
        return words;
    }

}
