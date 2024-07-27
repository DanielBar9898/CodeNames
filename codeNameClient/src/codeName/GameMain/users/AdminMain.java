package codeName.GameMain.users;

import DTO.GameStatusDTO;
import codeName.HttpClient.ActiveGames;
import codeName.HttpClient.FileUpload;
import codeName.HttpClient.GameStatus;
import codeName.HttpClient.ShowAllGames;
import com.google.gson.Gson;
import DTO.GameDTO;
import DTO.TeamDTO;
import engine.GamePackage.Player;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static javafx.application.Platform.exit;

public class AdminMain {

    public static void main(String[] args) throws IOException {
        showAdminMenu();
        Scanner sc = new Scanner(System.in);
        String fileName = null;
        String response = null;
        int choice = sc.nextInt();
        int gameNumber;
        switch (choice) {
            case 1:
                System.out.println("Enter XML file path:");
                sc.nextLine();
                fileName = sc.nextLine();
                System.out.println(new FileUpload(fileName).uploadFile());
                break;
            case 2:
                response = new ShowAllGames().showAllGames();
                printAllGamesDetails(response);
                break;
            case 3:

                response = new ActiveGames().showActiveGames();
                printActiveGameDetails(response);
                if (!response.equalsIgnoreCase("{\"error\": \"No active games\"}")) {
                    System.out.println("Please select the number of the game you would like to watch:");
                    sc.nextLine();
                    gameNumber = sc.nextInt();

                    String gameResponse = new ActiveGames().selectActiveGame(gameNumber);
                    int gameSerialNumber = extractGameSerialNumber(gameResponse);
                    Player player=new Player("Admin", Player.Role.DEFINER,gameSerialNumber);
                    if (gameSerialNumber != -1) {
                        String gameStatusResponse = new GameStatus().getGameStatus(gameSerialNumber);
                        Gson gson = new Gson();
                        GameStatusDTO gameStatus = gson.fromJson(gameStatusResponse, GameStatusDTO.class);
                        UserPlayGame.printGameStatus(gameStatus);
                        UserPlayGame.displayBoard(player, gson);
                    } else {
                        System.out.println("Failed to extract game serial number.");
                    }

                }
                break;
            case 4:
                System.out.println("Thank you for playing!");
                exit();
        }
    }
    private static int extractGameSerialNumber(String jsonResponse) {
        Gson gson = new Gson();
        GameDTO gameDTO = gson.fromJson(jsonResponse, GameDTO.class);
        return gameDTO.getGameSerialNumber();
    }

    public static void printAllGamesDetails(String jsonResponse) {
        Gson gson = new Gson();
        GameDTO[] gamesArray = gson.fromJson(jsonResponse, GameDTO[].class);
        int gameIndex=1;
        for (GameDTO game : gamesArray) {
            System.out.println("Game " + gameIndex + ":");
            printDetails(game);
            gameIndex++;
        }
    }
    public static void printSingleGameDetail(String jsonResponse) {
        Gson gson = new Gson();
        GameDTO game = gson.fromJson(jsonResponse, GameDTO.class);
        printDetails(game);
    }
    private static void printDetails(GameDTO game) {
        System.out.println("1. Game name: " + game.getName());
        System.out.println("2. Game status: " + (game.isActive() ? "Active" : "Pending"));
        System.out.println("3. Board details: " + game.getNumRows() + "X" + game.getNumCols());
        System.out.println("4. Dictionary file name: " + game.getDictName() + ", Unique words: " + game.getGameWordsCount());
        System.out.println("5. Normal words: " + game.getGameWordsCount() + ", Black words: " + game.getBlackWordsCount());
        System.out.println("6. Teams details:");
        for (TeamDTO team : game.getTeams()) {
            System.out.println("  a. Team name: " + team.getTeamName());
            System.out.println("  b. Words to guess: " + team.getWordsToGuess());
            System.out.println("  c. Definers required: " + team.getNumOfDefiners());
            System.out.println("  d. Guessers required: " + team.getNumOfGuessers());
        }
        System.out.println();
    }
    private static void printActiveGameDetails(String jsonResponse) {
        Gson gson = new Gson();
        GameDTO[] gamesArray = gson.fromJson(jsonResponse, GameDTO[].class);
        List<GameDTO> games = Arrays.asList(gamesArray);

        int gameIndex = 1;
        for (GameDTO game : games) {
            int activeTeams = 0;
            for (TeamDTO team : game.getTeams()) {
                if (team.getNumOfRegisteredGuessers() > 0 || team.getNumOfRegisteredDefiners() > 0) {
                    activeTeams++;
                }
            }

            System.out.println(gameIndex + ". Game name: " + game.getName());
            System.out.println("   Active teams: " + activeTeams + " / " + game.getTeams().size());
            gameIndex++;
        }
    }


    public static void showAdminMenu() {
        System.out.println("Admin Menu:\n");
        System.out.println("1.Load XML+TXT file\n2.Show active games info\n" +
                "3.Watch an active game\n4.Exit\nPlease enter your choice:");
    }
}
