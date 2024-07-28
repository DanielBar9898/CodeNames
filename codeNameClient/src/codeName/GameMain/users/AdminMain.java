package codeName.GameMain.users;

import DTO.GameStatusDTO;
import codeName.HttpClient.*;
import com.google.gson.Gson;
import DTO.GameDTO;
import DTO.TeamDTO;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
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
        boolean exit = false;
        boolean first = true;
        while(!exit) {
            if(!first){
                showAdminMenu();
                choice = sc.nextInt();
                first = false;
            }
            switch (choice) {
                case 1:
                    System.out.println("Enter XML file path:");
                    sc.nextLine();
                    fileName = sc.nextLine();
                    System.out.println(new FileUpload(fileName).uploadFile());
                    first = false;
                    break;
                case 2:
                    response = new ShowAllGames().showAllGames();
                    printGameDetails(response);
                    first = false;
                    break;
                case 3:
                    response = new ActiveGames().showActiveGames();
                    printActiveGameDetails(response);
                    if (!response.startsWith("{\"message\":")) {
                        System.out.println("Please select the number of the game you would like to watch:");
                        sc.nextLine();
                        gameNumber = sc.nextInt();

                        String gameResponse = new ActiveGames().selectActiveGame(gameNumber);
                        int gameSerialNumber = extractGameSerialNumber(gameResponse);
                        Player player = new Player("Admin", Player.Role.DEFINER, gameSerialNumber);
                        if (gameSerialNumber != -1) {
                            String gameStatusResponse = new GameStatus().getGameStatus(gameSerialNumber);
                            Gson gson = new Gson();
                            GameStatusDTO gameStatus = gson.fromJson(gameStatusResponse, GameStatusDTO.class);
                            UserPlayGame.printGameStatus(gameStatus);
                            UserPlayGame.displayBoard(player, gson);
                        } else {
                            System.out.println("Failed to extract game serial number.");
                        }
                        first = false;
                    }
                    break;
                case 4:
                    response = new AdminDisconnect().disconnectAdmin();
                    System.out.println(response+"Bye Bye");
                    exit = true;
                    exit();
            }
        }
    }
    private static int extractGameSerialNumber(String jsonResponse) {
        Gson gson = new Gson();
        GameDTO gameDTO = gson.fromJson(jsonResponse, GameDTO.class);
        return gameDTO.getGameSerialNumber();
    }

    private static void printGameDetails(String jsonResponse) {
        Gson gson = new Gson();
        try {
            // Check if the response is an array or an object
            if (jsonResponse.trim().startsWith("[")) {
                // Parse it as an array of GameDTO
                GameDTO[] gamesArray = gson.fromJson(jsonResponse, GameDTO[].class);
                List<GameDTO> games = Arrays.asList(gamesArray);

                for (GameDTO game : games) {
                    int totalWords = game.getGameSetSize();
                    int totalBlack = game.getBlackWordsCount();
                    int sum = totalWords - totalBlack;
                    System.out.println("1. Game name: " + game.getName());
                    System.out.println("2. Game status: " + (game.isActive() ? "Active" : "Pending"));
                    System.out.println("3. Board details: " + game.getNumRows() + "X" + game.getNumCols());
                    System.out.println("4. Dictionary file name: " + game.getDictName() + ", Unique words: " + game.getGameWordsCount());
                    System.out.println("5. Normal words: " + sum + ", Black words: " + totalBlack);
                    System.out.println("6. Teams details:");
                    for (TeamDTO team : game.getTeams()) {
                        System.out.println("  a. Team name: " + team.getTeamName());
                        System.out.println("  b. Words to guess: " + team.getWordsToGuess());
                        System.out.println("  c. Definers required: " + team.getNumOfDefiners());
                        System.out.println("  d. Guessers required: " + team.getNumOfGuessers());
                    }
                    System.out.println();
                }
            } else {
                // Parse it as an object
                JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

                // Check if the JSON contains a message
                if (jsonObject.has("message")) {
                    String message = jsonObject.get("message").getAsString();
                    System.out.println("Message: " + message);
                    return;
                }
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Invalid JSON response: " + e.getMessage());
        }
    }

    private static void printActiveGameDetails(String jsonResponse) {
        Gson gson = new Gson();
        try {
            if (jsonResponse.trim().startsWith("[")) {
                // Parse it as an array of GameDTO
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
            } else {
                // Parse it as an object
                JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

                // Check if the JSON contains a message
                if (jsonObject.has("message")) {
                    String message = jsonObject.get("message").getAsString();
                    System.out.println("Message: " + message);
                    return;
                }

                // If it doesn't contain a message, assume it's a single GameDTO object
                GameDTO game = gson.fromJson(jsonResponse, GameDTO.class);
                int activeTeams = 0;
                for (TeamDTO team : game.getTeams()) {
                    if (team.getNumOfRegisteredGuessers() > 0 || team.getNumOfRegisteredDefiners() > 0) {
                        activeTeams++;
                    }
                }

                System.out.println("1. Game name: " + game.getName());
                System.out.println("   Active teams: " + activeTeams + " / " + game.getTeams().size());
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Invalid JSON response: " + e.getMessage());
        }
    }

    public static void showAdminMenu() {
        System.out.println("Admin Menu:\n");
        System.out.println("1.Load XML+TXT file\n2.Show active games info\n" +
                "3.Watch an active game\n4.Exit\nPlease enter your choice:");
    }
}
