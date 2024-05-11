package EnginePackage;
import GamePackage.Game;
import GamePackage.Team;
import JAXBGenerated.ECNGame;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;


public class EngineImpl implements Engine {
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "JAXBGenerated";

    public boolean loadXmlFile(String fileName){
            if(!(fileName.endsWith(".xml"))){
                System.out.println(fileName + " is not a valid XML file");
                return false;
            }
            else{
                try{
                    InputStream inputStream = new FileInputStream(new File(fileName));
                    Game game = deserializeGame(inputStream);
                    boolean valid = game.validateFile();
                    return valid;
                }
                catch(JAXBException | FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        return false;
    }
    private static Game deserializeGame(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        ECNGame g = (ECNGame) unmarshaller.unmarshal(inputStream);
        return new Game(g);
    }

    public void showGameMenu(){
        return;
    }

    public void startGame(){
        return;
    }

    public void playTurn(Team teamTurn){
        teamTurn.printTeamTurn();
        teamTurn.showTeamWordsState();
        teamTurn.getHinter().printBoard();
        teamTurn.getHinter().playTurn();
        teamTurn.getGuesser().printBoard();
        teamTurn.getGuesser().playTurn();
    }

    public void printGameStats(){
        return;
    }

}
