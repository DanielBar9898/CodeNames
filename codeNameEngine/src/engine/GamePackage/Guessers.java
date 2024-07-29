package engine.GamePackage;

import java.util.List;

public class Guessers extends Player {
    private Word.cardColor color;
    Board hiddenBoard;

    public Guessers(String name, Role role, int gameSerialNumber, String teamOfPlayer) {
        super(name, role, gameSerialNumber, teamOfPlayer);
    }

    // Getters and Setters
    public Board getHiddenBoard() {
        return hiddenBoard;
    }


    public boolean checkGuess(List<Integer> wordsIndexes) {
        return false;
    }

    public Word.cardColor getColor() {
        return color;
    }

    public void setColor(Word.cardColor color) {
        this.color = color;
    }
}
