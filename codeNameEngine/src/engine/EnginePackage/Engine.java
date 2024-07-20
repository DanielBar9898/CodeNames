package engine.EnginePackage;
import engine.GamePackage.Game;
import engine.GamePackage.Team;

public interface Engine {

    public Game loadXmlFile (String filename);

    public void showGameMenu();

    public String showLoadedGameInfo(Game currentGame);

    public void startGame(Game currentGame);

    public void playTurn(Team teamTurn, String hint, int numOfWordsToGuess);

    public boolean playTurn(Team teamTurn ,int wordIndex,BooleanWrapper gameOver);

    public void printGameStats(Game currentGame,boolean team1Turn);


}
