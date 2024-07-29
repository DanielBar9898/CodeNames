package engine.EnginePackage;

import engine.GamePackage.Board;
import engine.GamePackage.Game;
import engine.GamePackage.Team;
import engine.GamePackage.Word;
import engine.JAXBGenerated2.ECNGame;
import engine.users.User;
import codeName.utils.Response;
import com.google.gson.Gson;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class EngineImpl implements Engine {
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "engine.JAXBGenerated2";
    Set<Game> games;
    Set<User> users;

    public Game loadXmlFile(String fileName){
            if(!(fileName.endsWith(".xml"))){
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
        response.addMessage("For the guess index number : " + wordIndex+"\n");
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
            response.addMessage("OMG! It's a black word, game over!\n");
            gameOver.setValue(true);
            response.setGameOver(true);
            otherTeamWord = false;
        } else if (currWord.getWordType().equalsIgnoreCase("Neutral\n")) {
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


}

