package engine.EnginePackage;

import engine.GamePackage.Board;
import engine.GamePackage.Game;
import engine.GamePackage.Team;
import engine.GamePackage.Word;
import engine.JAXBGenerated2.ECNGame;
//import engine.users.User;
import codeName.utils.Response;
import com.google.gson.Gson;
import jdk.internal.org.xml.sax.InputSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.nio.file.*;

public class EngineImpl implements Engine {
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "engine.JAXBGenerated2";
    Set<Game> games;
  //  Set<User> users;

    public Game loadXmlFile(String fileName,String txtName,PrintWriter out){
            if(!(fileName.endsWith(".xml"))){
                out.write("This is not an XML file!\n");
                return null;
            }
            else if(!isFileInDirectory(extractFilePath(fileName),txtName)){
                out.write("Could not find " +txtName+" in working directory!\n");
                return null;
            }
            else{
                try{
                    InputStream inputStream = new FileInputStream(new File(fileName));
                    Game g = deserializeGame(inputStream);
                    return g;
                }
                catch(JAXBException | FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        return null;
    }

    private static Game deserializeGame(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        ECNGame g = (ECNGame) unmarshaller.unmarshal(inputStream);
        return new Game(g);
    }

    public void showGameMenu(){
        System.out.println("\nPlease select one of the following options:");
        System.out.println("\t1. Load XML file");
        System.out.println("\t2. Show game information");
        System.out.println("\t3. Start New game");
        System.out.println("\t4. Play turn");
        System.out.println("\t5. Show active game statistics");
        System.out.println("\t6. Exit");


    }

    public String showLoadedGameInfo(Game currentGame){
        String s = new String("Game information");
        s = s+ currentGame.toString();
        return s;
    }

    public void startGame(Game currentGame){
//        Team team1 = currentGame.getTeam1();
//        Team team2 = currentGame.getTeam2();
//        currentGame.getGameBoard().assignWordsToTeams(team1, team2);
    }

    public synchronized void playTurn(Team teamTurn, String hint, int numOfWordsToGuess , Response response){
        teamTurn.getDefiner().setHint(hint);
        teamTurn.getDefiner().setWordsHint(numOfWordsToGuess);
    }


    public synchronized boolean playTurn(Team teamTurn, int wordIndex, BooleanWrapper gameOver, Response response, Board teamBoard,
                                         ArrayList<Team> teams) {
        boolean otherTeamWord;
        Word currWord;
        Set<Word> wordsSet = teamTurn.getWordsNeedToGuess();
        currWord = teamBoard.getWordBySerialNumber(wordIndex);
        if (currWord == null) {
            response.addMessage("Invalid word index!\n");
            otherTeamWord = false;
        } else if (currWord.isFound()) {
            response.addMessage("Someone already guessed the word\n");
            otherTeamWord = false;
        } else if (wordsSet.contains(currWord)) {
            response.addMessage("It's your team word! You've guessed correctly and earned your team 1 point!\n");
            teamTurn.guessedRight();
            otherTeamWord = true;
        } else if (currWord.getWordType().equalsIgnoreCase("Black")) {
            response.addMessage("OMG! It's a black word, game over for your team!\n");
            gameOver.setValue(true);
            response.setGameOver(true);
            otherTeamWord = false;
            teamTurn.guessedBlackWord();
        } else if (currWord.getWordType().equalsIgnoreCase("Neutral")) {
            response.addMessage("It's a neutral word");
            otherTeamWord = false;
        } else {
            response.addMessage("It's a word of the other team! You've earned them a point!\n");
            for(Team t : teams){
                if(currWord.getWordType().equalsIgnoreCase(t.getTeamName())){
                    t.guessedRight();
                }
            }
            otherTeamWord = true;
        }
        currWord.found();
        response.setOtherTeamWord(otherTeamWord);
        return otherTeamWord;
    }

    public void printGameStats(Game currentGame,boolean team1Turn){
        currentGame.getGameBoard().printTheBoard(false);
       List<Team> teams = new ArrayList<>();

    }
    public static boolean isFileInDirectory(String directoryPath, String fileName) {
        Path dirPath = Paths.get(directoryPath);
        Path filePath = dirPath.resolve(fileName);

        return Files.exists(filePath) && Files.isRegularFile(filePath);
    }
    public static String extractFilePath(String fullPath) {
        // Find the last separator index
        int lastSeparatorIndex = fullPath.lastIndexOf('\\');

        // Extract the directory path
        if (lastSeparatorIndex != -1) {
            return fullPath.substring(0, lastSeparatorIndex);
        } else {
            // If no separator is found, return an empty string or handle as needed
            return "";
        }
    }
}

