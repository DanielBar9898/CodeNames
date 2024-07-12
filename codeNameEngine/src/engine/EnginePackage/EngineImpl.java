package engine.EnginePackage;
import engine.GamePackage.Game;
import engine.GamePackage.Team;
import engine.GamePackage.Word;
import engine.JAXBGenerated.ECNGame;

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
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "engine/JAXBGenerated";

    public Game loadXmlFile(String fileName){
            if(!(fileName.endsWith(".xml"))){
                System.out.println(fileName + " is not a valid XML file");
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

    public void showLoadedGameInfo(Game currentGame){
        System.out.println("Game information:");
        System.out.println(currentGame);
    }

    public void startGame(Game currentGame){
        Team team1 = currentGame.getTeam1();
        Team team2 = currentGame.getTeam2();
        currentGame.getGameBoard().assignWordsToTeams(team1, team2);
    }

    public void playTurn(Team teamTurn, String hint, int numOfWordsToGuess){
        teamTurn.showTeamWordsState();
        teamTurn.getHinter().playHinterTurn(hint, numOfWordsToGuess);
        teamTurn.getHinter().getHint();
    }


    public boolean playTurn(Team teamTurn ,int wordIndex,BooleanWrapper gameOver){
        boolean otherTeamWord;
        Word currWord;
        Scanner sc = new Scanner(System.in);
        Set<Word> wordsSet = teamTurn.getWordsNeedToGuess();
        int validInput=wordsSet.size();
        currWord = teamTurn.getTeamBoard().getWordBySerialNumber(wordIndex);
        if(currWord==null){
            System.out.println("Invalid word index!");
           // checkIndexInput(teamTurn,wordIndex,gameOver,validInput);
            otherTeamWord = false;
        }
        else if(currWord.isFound()){
           // System.out.println("Someone already guessed the word, please choose another one:");
            otherTeamWord=false;
        }
        else if(wordsSet.contains(currWord)){
            System.out.println("Its your team word! you've guessed correctly and earned your team 1 point!");
            teamTurn.guessedRight();
            otherTeamWord = true;
        }
        else if(currWord.getColor()==Word.cardColor.BLACK){
            System.out.println("OMG! its a black word, game over!");
            gameOver.setValue(true);
            otherTeamWord = false;
        }
        else if(currWord.getColor()==Word.cardColor.NEUTRAL){
            System.out.println("Its a neutral word");
            otherTeamWord = false;
        }
        else{
            System.out.println("Its a word of the other team! you've earned them a point!");
            otherTeamWord = true;
        }
        currWord.found();
        return otherTeamWord;
    }

    public void printGameStats(Game currentGame,boolean team1Turn){
        currentGame.getGameBoard().printTheBoard(false);
       List<Team> teams = new ArrayList<>();
       teams.add(currentGame.getTeam1());
       teams.add(currentGame.getTeam2());
       for(Team t:teams){
           System.out.println(t.getTeamName());
           System.out.println(t.howManyWordsGuessed());
           System.out.println("The team has played "+t.getNumOfTurns()+" turns");
       }
       if(team1Turn){
           System.out.println("Next turn is "+currentGame.getTeam1().getTeamName()+ " turn");
       }
       else{
           System.out.println("Next turn is "+currentGame.getTeam2().getTeamName()+ " turn");
       }
    }


}

