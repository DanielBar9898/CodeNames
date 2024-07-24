package codeName.utils;

import java.util.ArrayList;
import java.util.List;

public class Response {
    private List<String> messages;
    private boolean otherTeamWord;
    private boolean gameOver;

    public Response() {
        this.messages = new ArrayList<>();
        this.otherTeamWord = false;
        this.gameOver = false;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }

    public boolean isOtherTeamWord() {
        return otherTeamWord;
    }

    public void setOtherTeamWord(boolean otherTeamWord) {
        this.otherTeamWord = otherTeamWord;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
