package DTO;

public class GameStatusDTO {
    private String gameStatus;
    private String currentTeamName;
    private String currentTeamScore;
    private int currentTeamTurns;
    private String nextTeamName;
   // private BoardDTO board;

    public GameStatusDTO(String gameStatus, String currentTeamName, String currentTeamScore, int currentTeamTurns, String nextTeamName/*, BoardDTO board*/) {
        this.gameStatus = gameStatus;
        this.currentTeamName = currentTeamName;
        this.currentTeamScore = currentTeamScore;
        this.currentTeamTurns = currentTeamTurns;
        this.nextTeamName = nextTeamName;
       // this.board = board;
    }

    // Getters and Setters
    public String getGameStatus() { return gameStatus; }
    public void setGameStatus(String gameStatus) { this.gameStatus = gameStatus; }
    public String getCurrentTeamName() { return currentTeamName; }
    public void setCurrentTeamName(String currentTeamName) { this.currentTeamName = currentTeamName; }
    public String getCurrentTeamScore() { return currentTeamScore; }
    public void setCurrentTeamScore(String currentTeamScore) { this.currentTeamScore = currentTeamScore; }
    public int getCurrentTeamTurns() { return currentTeamTurns; }
    public void setCurrentTeamTurns(int currentTeamTurns) { this.currentTeamTurns = currentTeamTurns; }
    public String getNextTeamName() { return nextTeamName; }
    public void setNextTeamName(String nextTeamName) { this.nextTeamName = nextTeamName; }
   // public BoardDTO getBoard() { return board; }
    //public void setBoard(BoardDTO board) { this.board = board; }
}
