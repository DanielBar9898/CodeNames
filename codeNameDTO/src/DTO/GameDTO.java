package DTO;

import java.util.ArrayList;

public class GameDTO {
    private String name;
    private int blackWordsCount;
    private int gameWordsCount;
    private boolean active;
    private String dictName;
    private ArrayList<TeamDTO> teams;
    private int gameSerialNumber;
    private int numRows;
    private int numCols;
    private int gameSetSize;

    public GameDTO(String name, int blackWordsCount, int gameWordsCount, boolean active, String dictName, ArrayList<TeamDTO> teams, int gameSerialNumber, int numRows, int numCols, int wordsSize) {
        this.name = name;
        this.blackWordsCount = blackWordsCount;
        this.gameWordsCount = gameWordsCount;
        this.active = active;
        this.dictName = dictName;
        this.teams = teams;
        this.gameSerialNumber = gameSerialNumber;
        this.numRows = numRows;
        this.numCols = numCols;
        this.gameSetSize = wordsSize;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getBlackWordsCount() { return blackWordsCount; }
    public void setBlackWordsCount(int blackWordsCount) { this.blackWordsCount = blackWordsCount; }
    public int getGameWordsCount() { return gameWordsCount; }
    public void setGameWordsCount(int gameWordsCount) { this.gameWordsCount = gameWordsCount; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public String getDictName() { return dictName; }
    public void setDictName(String dictName) { this.dictName = dictName; }
    public ArrayList<TeamDTO> getTeams() { return teams; }
    public void setTeams(ArrayList<TeamDTO> teams) { this.teams = teams; }
    public int getGameSerialNumber() { return gameSerialNumber; }
    public void setGameSerialNumber(int gameSerialNumber) { this.gameSerialNumber = gameSerialNumber; }
    public int getNumRows() { return numRows; }
    public void setNumRows(int numRows) { this.numRows = numRows; }
    public int getNumCols() { return numCols; }
    public void setNumCols(int numCols) { this.numCols = numCols; }
    public int getGameSetSize() { return gameSetSize; }
}
