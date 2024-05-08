package EnginePackage;
import GamePackage.*;
public interface Engine {

    public void loadXmlFile(String fileName);

    public void showGameMenu();

    public void startGame();

    public void playTurn(Team teamTurn);

    public void printGameStats();


}
