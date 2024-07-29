package engine.EnginePackage;
import codeName.utils.Response;
import engine.GamePackage.Board;
import engine.GamePackage.Game;
import engine.GamePackage.Team;

import java.util.ArrayList;

public interface Engine {

    public Game loadXmlFile (String filename);

    public void showGameMenu();

    public String showLoadedGameInfo(Game currentGame);

    public void startGame(Game currentGame);

    public void playTurn(Team teamTurn, String hint, int numOfWordsToGuess , Response response);

    public boolean playTurn(Team teamTurn , int wordIndex, BooleanWrapper gameOver , Response response, Board teamBoard,
                            ArrayList<Team> teams);

    public void printGameStats(Game currentGame,boolean team1Turn);


}
