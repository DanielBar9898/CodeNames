package codeName.GameMain.users;
import codeName.HttpClient.*;
import com.google.gson.Gson;
import DTO.GameDTO;
import DTO.TeamDTO;
import engine.GamePackage.Player;

import java.sql.Struct;
import java.util.InputMismatchException;

import java.io.IOException;
import java.util.Scanner;

import static javafx.application.Platform.exit;

public class UserMain {
    public static void main(String[] args) {
        UserMain userMain = new UserMain();
        try {
            userMain.userMainMenu();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void userMainMenu() throws IOException {
        Scanner sc = new Scanner(System.in);
        String username = getUsername();
        String response;
        Player player = null;
        boolean newGame = false;
        int gameNumber = 0, teamNumber;
        showUserMenu();
        boolean exit = false;
        while(!exit) {
            int choice = getValidChoice(sc);
            switch (choice) {
                case 1:
                    AdminMain.printGameDetails(new ShowAllGames().showAllGames());
                    break;
                case 2:
                    try {
                        response = new PendingGames().showPendingGames();
                        printAllPendingGamesDetails(response);
                        if (!response.equalsIgnoreCase("{\"message\":\"No pending games\"}")) {
                            gameNumber = selectGame(sc);
                            if (gameNumber != 0) {
                                teamNumber = selectTeam(sc, gameNumber);
                                if (teamNumber != 0) {
                                    String role = selectRole(sc, gameNumber, teamNumber);
                                    if (!role.isEmpty()) {
                                        response = new JoinGame().joinGame(username, gameNumber, teamNumber, role);
                                        System.out.println(response);
                                        String teamName = extractTeamNameFromResponse(response);
                                        if (role.equalsIgnoreCase("Guesser"))
                                            player = new Player(username, Player.Role.GUESSER, gameNumber, teamName);
                                        else
                                            player = new Player(username, Player.Role.DEFINER, gameNumber, teamName);
                                        newGame = true;
                                    } else {
                                        System.out.println("Role selection canceled.");
                                    }
                                } else {
                                    System.out.println("Team selection canceled.");
                                }
                            } else {
                                System.out.println("Game selection canceled.");
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Thank you for playing!");
                    exit();
            }
            if (newGame) {
                new UserPlayGame().userGameMenu(player);
            }
        }
    }


    private int selectGame(Scanner sc) {
        int gameNumber = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Please select the number of the game you would like to join (or 0 to cancel):");
            try {
                int selectedGameIndex = sc.nextInt();
                sc.nextLine(); // Consume newline

                if (selectedGameIndex == 0) {
                    return gameNumber; // Return 0 to cancel
                }

                String gameDetails = new PendingGames().selectPendingGame(selectedGameIndex);

                // Check if the response contains an error message
                if (gameDetails.contains("\"error\"")) {
                    System.out.println("Game not found. Please enter a valid game number.");
                    continue; // Ask for input again
                }

                System.out.println("Selected Game Details:");
                printSingelPendingGameDetails(gameDetails);

                // Parse the JSON response to get the game serial number
                Gson gson = new Gson();
                GameDTO game = gson.fromJson(gameDetails, GameDTO.class);
                gameNumber = game.getGameSerialNumber();

                validInput = true;
            } catch (InputMismatchException e) {
                sc.nextLine(); // Clear the invalid input
                System.out.println("Invalid input. Please enter a valid game number.");
            } catch (IOException e) {
                System.out.println("Game not found. Please enter a valid team number. ");
            }
        }
        return gameNumber;
    }


    private int selectTeam(Scanner sc, int gameNumber) {
        int teamNumber = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.println("Please select the number of the team you would like to join (or 0 to cancel):");
            try {
                teamNumber = sc.nextInt();
                sc.nextLine(); // Consume newline

                if (teamNumber == 0) {
                    return teamNumber;
                }

                String teamDetails = new SelectTeam().selectTeam(gameNumber, teamNumber);
                // Check if the response contains an error message
                if (teamDetails.contains("\"Full\"")) {
                    System.out.println("This team is already Full. Please enter a valid team number.");
                    continue; // Ask for input again
                }
                else if (teamDetails.contains("\"error\"")) {
                    System.out.println("Team not found. Please enter a valid team number.");
                    continue; // Ask for input again
                }
                System.out.println("Selected Team Details:");
                printTeamDetails(teamDetails);

                validInput = true;
            } catch (InputMismatchException e) {
                sc.nextLine(); // Clear the invalid input
                System.out.println("Invalid input. Please enter a valid team number.");
            } catch (IOException e) {
                System.out.println("Team not found. Please enter a valid team number. ");
            }
        }
        return teamNumber;
    }

    private static void printTeamDetails(String jsonResponse) {
        Gson gson = new Gson();
        TeamDTO team = gson.fromJson(jsonResponse, TeamDTO.class);

        System.out.println("  Team name: " + team.getTeamName());
        System.out.println("  Words to guess: " + team.getWordsToGuess());
        System.out.println("  Definers required: " + team.getNumOfDefiners() + ", Registered definers: " + team.getNumOfRegisteredDefiners());
        System.out.println("  Guessers required: " + team.getNumOfGuessers() + ", Registered guessers: " + team.getNumOfRegisteredGuessers());
    }

    private String selectRole(Scanner sc, int gameNumber, int teamNumber) {
        String role = "";
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Please select the role you would like to join (1 for Definer, 2 for Guesser, or 0 to cancel):");
            try {
                int roleChoice = sc.nextInt();
                sc.nextLine();

                if (roleChoice == 0) {
                    return "";
                } else if (roleChoice == 1) {
                    role = "Definer";
                } else if (roleChoice == 2) {
                    role = "Guesser";
                } else {
                    System.out.println("Invalid choice. Please enter 1 for Definer or 2 for Guesser.");
                    continue;
                }

                String roleDetails = new SelectRole().selectRole(gameNumber, teamNumber, role);
                if (roleDetails.contains("\"error\":")) {
                    System.out.println("The role you selected cannot be selected. Please enter 1 for Definer or 2 for Guesser." );
                    continue;
                }

                validInput = true;
            } catch (InputMismatchException e) {
                sc.nextLine(); // Clear the invalid input
                System.out.println("Invalid input. Please enter 1 for Definer or 2 for Guesser.");
            } catch (IOException e) {
                System.out.println("The role you selected cannot be selected. Please enter a valid role number.");
            }
        }
        return role;
    }

    public String getUsername() {
        String username = null;
        boolean isUnique = false;
        Scanner sc = new Scanner(System.in);
        UserNameList userNameList = new UserNameList();

        do {
            System.out.println("Please enter your username:");
            username = sc.nextLine();
            try {
                String response = userNameList.addUserName(username);
                if (response.contains("Username added successfully")) {
                    isUnique = true;
                } else {
                    System.out.println("Username already exists, please choose another username.");
                }
            } catch (IOException e) {
                System.out.println("Error occurred while checking username. Please try again.");
                e.printStackTrace();
            }
        } while (!isUnique);

        return username;
    }
    private String extractTeamNameFromResponse(String response) {
        String[] parts = response.split(" ");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equalsIgnoreCase("team")) {
                return parts[i + 1]; // The name of the team is the word after "team"
            }
        }
        return "";
    }

    public static void showUserMenu(){
        System.out.println("User Menu:\n");
        System.out.println("1.Show All games info\n2.Join a game!\n" +
                "3.Exit\nPlease enter your choice:");
    }
    public static void printAllPendingGamesDetails(String jsonResponse) {
        Gson gson = new Gson();
        GameDTO[] gamesArray = gson.fromJson(jsonResponse, GameDTO[].class);
        int gameIndex = 1;
        for (GameDTO game : gamesArray) {
            System.out.println("Game " + gameIndex + ":");
            printPendingDetails(game);
            gameIndex++;
        }
    }
    public static void printSingelPendingGameDetails(String jsonResponse) {
        Gson gson = new Gson();
        GameDTO game = gson.fromJson(jsonResponse, GameDTO.class);
        printPendingDetails(game);
    }
    private static int getValidChoice(Scanner sc) {
        int choice = 0;
        boolean valid = false;
        while (!valid) {
            try {
                choice = sc.nextInt();
                if (choice >= 1 && choice <= 3) {
                    valid = true;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 4:");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 4:");
                sc.next(); // Clear the invalid input
            }
        }
        return choice;
    }
    private static void printPendingDetails(GameDTO game) {

        System.out.println("1. Game name: " + game.getName());
        System.out.println("2. Game status: " + (game.isActive() ? "Active" : "Pending"));
        System.out.println("3. Board details: " + game.getNumRows() + "X" + game.getNumCols());
        System.out.println("4. Regular words: " + game.getGameWordsCount() + ", Black words: " + game.getBlackWordsCount());
        System.out.println("5. Teams details:");

        int teamIndex = 1;
        for (TeamDTO team : game.getTeams()) {
            System.out.println("  Team " + teamIndex + ":");
            System.out.println("    a. Team name: " + team.getTeamName());
            System.out.println("    b. Words to guess: " + team.getWordsToGuess());
            System.out.println("    c. Definers required: " + team.getNumOfDefiners() + ", Registered definers: " + team.getNumOfRegisteredDefiners());
            System.out.println("    d. Guessers required: " + team.getNumOfGuessers() + ", Registered guessers: " + team.getNumOfRegisteredGuessers());
            teamIndex++;
        }
        System.out.println();

    }
}
