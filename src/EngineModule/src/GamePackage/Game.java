package GamePackage;

public class Game {
    Team team1;
    Team team2;
    int gameWords;
    int blackWords;
    Board gameBoard;

    public Game(Team t1, Team t2, int gw, int bw, Board board) {
        team1 = t1;
        team2 = t2;
        gameWords = gw;
        blackWords = bw;
        gameBoard = board;
    }


    public void showGameInfo(){
        System.out.println("1. Game words: " + gameWords);
        System.out.println("2. Black words: " + blackWords);
        System.out.println("Team 1: \nName:" + team1+"\nWords to guess:"+team1.getWordsCount());
        System.out.println("Team 2: \nName:" + team2+"\nWords to guess:"+team2.getWordsCount());
    }
}
