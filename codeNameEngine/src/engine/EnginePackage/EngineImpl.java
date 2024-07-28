package engine.EnginePackage;

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
        response.addMessage("So far the team guessed correctly "+teamTurn.getWordsGuessed()+"/"+teamTurn.getWordsToGuess()+" words");
        response.addMessage("The hint is : " + hint + ", number of words related are : " + numOfWordsToGuess);
    }


    public synchronized boolean playTurn(Team teamTurn, int wordIndex, BooleanWrapper gameOver, Response response) {
        boolean otherTeamWord;
        Word currWord;
        Set<Word> wordsSet = teamTurn.getWordsNeedToGuess();
        currWord = teamTurn.getTeamBoard().getWordBySerialNumber(wordIndex);

        if (currWord == null) {
            response.addMessage("Invalid word index!");
            otherTeamWord = false;
        } else if (currWord.isFound()) {
            response.addMessage("Someone already guessed the word, please choose another one:");
            otherTeamWord = false;
        } else if (wordsSet.contains(currWord)) {
            response.addMessage("It's your team word! You've guessed correctly and earned your team 1 point!");
            teamTurn.guessedRight();
            otherTeamWord = true;
        } else if (currWord.getColor() == Word.cardColor.BLACK) {
            response.addMessage("OMG! It's a black word, game over!");
            gameOver.setValue(true);
            response.setGameOver(true);
            otherTeamWord = false;
        } else if (currWord.getColor() == Word.cardColor.NEUTRAL) {
            response.addMessage("It's a neutral word");
            otherTeamWord = false;
        } else {//Need to implement how to increase the points of the opposite team
            response.addMessage("It's a word of the other team! You've earned them a point!");
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

