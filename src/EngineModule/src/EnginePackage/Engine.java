package EnginePackage;
import GamePackage.*;
public interface Engine {

    public boolean loadXmlFile ();

    public void showGameMenu();

    public void startGame();

    public void playTurn(Team teamTurn);

    public void printGameStats();


}
