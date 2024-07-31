package codeName.GameMain.users;

import DTO.BoardDTO;
import DTO.GameStatusDTO;
import DTO.WordDTO;
import codeName.HttpClient.Http.*;
import com.google.gson.Gson;
import engine.GamePackage.Board;
import engine.GamePackage.Player;
import engine.GamePackage.Word;

import java.io.IOException;
import java.util.*;

public class UserPlayGame {
    public void userGameMenu(Player player) throws IOException {
        System.out.println("Welcome!\n");
        showUserPlayGameMenu();
        Scanner sc = new Scanner(System.in);
        int numOfWords;
        String response , hint;
        int guess , currentNumOfWords;
        int choice;
        boolean first = true;
        choice = sc.nextInt();
        String teamName = player.getTeamOfPlayer();
        String playingTeam , rsp;
        String currentHint;
        if(player == null){
            return;
        }
        int gameNumber = player.getSerialGameNumber();
        boolean gameOver = false;
        Set <String> teamsOut = new HashSet<>();
        while(!gameOver){
            if(!first){
                showUserPlayGameMenu();
                choice = sc.nextInt();
            }
            first = false;
            String gameStatusJson = new GameStatus().getGameStatus(gameNumber);
            Gson gson = new Gson();
            GameStatusDTO gameStatus = gson.fromJson(gameStatusJson, GameStatusDTO.class);
            switch(choice) {
                case 1:
                    printGameStatus(gameStatus);
                    if (gameStatus.getGameStatus().equalsIgnoreCase("Active"))
                        displayBoard(player, gson);
                    break;
                case 2:
                    playingTeam = new PlayingTeamTurn().playingTeamTurn(gameNumber);
                    if(gameStatus.getGameStatus().equalsIgnoreCase("Active")){
                        if(!playingTeam.
                                equalsIgnoreCase(player.getTeamOfPlayer()))
                            System.out.println("Its not your team turn! its "+ playingTeam +" turn!");
                        else{
                            response = new GetNextTurn().nextTurn(teamName);
                            if(player.getRole() == Player.Role.DEFINER){
                                if(response.equalsIgnoreCase("Definer")){
                                    printInfoOfTeam(gameNumber,teamName);
                                    displayBoard(player, gson);
                                    sc.nextLine();
                                    System.out.println("Put your hint:");
                                    hint = sc.nextLine();
                                    System.out.println("How many words related?");
                                    numOfWords = getValidatedInteger();
                                    response = new PlayTurn().playTurnDefiner(player, hint, numOfWords);
                                    new SwitchTurn().switchTurn(teamName);
                                    System.out.println(response);
                                }
                                else{
                                    System.out.println("Wait for a guesser to guess!");
                                }
                            }
                            else{
                                if(response.equalsIgnoreCase("Guesser")){
                                    currentHint ="The hint is " + new DisplayHint().displayHint(teamName);
                                    System.out.println(currentHint);
                                    displayBoard(player, gson);
                                    currentNumOfWords = extractNumberOfWords(currentHint);
                                    while(currentNumOfWords > 0 &&
                                            !response.equalsIgnoreCase("GAME OVER FOR YOUR TEAM!")){
                                        System.out.println("Put the index of the word you want to guess or 0 to stop turn");
                                        sc.nextLine();
                                        guess = sc.nextInt();
                                        if(guess == 0){
                                            currentNumOfWords = 0;
                                        }
                                        else{
                                            response = new PlayTurn().playTurnGuesser(player, guess);
                                            System.out.println(response);
                                            currentNumOfWords--;
                                        }
                                    }
                                    new SwitchTurn().switchTurn(teamName);
                                }
                                else{
                                    System.out.println("Wait for a definer to give a hint!");
                                }
                            }
                        }
                    }
                    else{
                        System.out.println("The game is not active yet!");
                    }
                    break;
                case 3:
                    response = new UserLogout().logoutPlayer(player);
                    System.out.println(response);
                    System.out.println("Thank you for playing game!");
                    System.exit(0);
                    break;
            }
            rsp = checkWordsState(gameNumber);
            if(!teamsOut.contains(rsp)){
                teamsOut.add(rsp);
                System.out.println(rsp);
            }
            if(checkTeams(gameNumber).equalsIgnoreCase("Not enough teams in the game!"))
            {
             System.out.println("Only 1 team left, game over!");
             gameOver = true;
             System.out.println(new DeactivateGame().deactivateGame(gameNumber));
            }
        }
    }
    public void logoutUser(Player player) throws IOException {
        UserLogout userLogout = new UserLogout();
        try {
            String response = userLogout.logoutPlayer(player);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void showUserPlayGameMenu(){
        System.out.println("Game Menu:\n");
        System.out.println("1.Show status game \n2.Play turn!\n" +
                "3.Exit\nPlease enter your choice:");
    }
    public static void printGameStatus(GameStatusDTO gameStatus) {
        System.out.println("Game status: " + gameStatus.getGameStatus());
        System.out.println("Current team: " + gameStatus.getCurrentTeamName());
        System.out.println("Current score: " + gameStatus.getCurrentTeamScore());
        System.out.println("Turns taken: " + gameStatus.getCurrentTeamTurns());
        System.out.println("Next team: " + gameStatus.getNextTeamName());

        if (gameStatus.getNextTeamName() == null || gameStatus.getNextTeamName().isEmpty()) {
            System.out.println("The game has ended. Returning to the main menu.");
        }
    }
    public static void displayBoard(Player player, Gson gson) throws IOException {
        String boardJson = new GetBoard().getBoard(player.getSerialGameNumber());
        BoardDTO boardDTO = gson.fromJson(boardJson, BoardDTO.class);
        Board board = convertBoardDTOToBoard(boardDTO);

        if (player.getRole() == Player.Role.DEFINER)
            board.printTheBoard(false); // Visible
        else
            board.printTheBoard(true); // Hidden
    }
    public static Board convertBoardDTOToBoard(BoardDTO boardDTO) {
        Set<Word> words = new HashSet<>();
        for (WordDTO wordDTO : boardDTO.getWords()) {
            Word word = new Word(wordDTO.getWord());
            word.setWordType(wordDTO.getWordType());
            word.setSerialNumber(wordDTO.getSerialNumber());
            if (wordDTO.isFound()) {
                word.found();
            }
            words.add(word);
        }

        Board board = new Board(boardDTO.getNumRows(), boardDTO.getNumCols(), boardDTO.getNumOfBlackWords(), boardDTO.getNumOfTotalWords(), words);
        return board;
    }
    public static int getValidatedInteger() {
        Scanner scanner = new Scanner(System.in);
        Integer userInput = null;

        while (userInput == null) {
            System.out.print("Please enter an integer: ");
            String input = scanner.nextLine();
            try {
                userInput = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter an integer.");
            }
        }

        return userInput;
    }
    public static void printInfoOfTeam (int gameNumber , String teamName) throws IOException {
        System.out.println(new ShowTeamInfo().showTeamInfo(gameNumber , teamName));
    }
    public static String checkWordsState(int gameNumber) throws IOException {
        return new CheckTeamsWords().playingTeamTurn(gameNumber);
    }
    public static String checkTeams(int gameNumber) throws IOException {
        return new CheckAmountOfTeams().checkTeams(gameNumber);
    }
    public static int extractNumberOfWords(String input) {
        // Regular expression to match the integer in the string
        String regex = ".*?(\\d+).*?";

        // Create a Pattern object
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);

        // Create a matcher object
        java.util.regex.Matcher matcher = pattern.matcher(input);

        // Check if the pattern matches
        if (matcher.find()) {
            // Return the captured group (the integer)
            return Integer.parseInt(matcher.group(1));
        } else {
            // If the pattern doesn't match, throw an exception or return a default value
            throw new IllegalArgumentException("Input string does not contain an integer");
        }
    }
}
