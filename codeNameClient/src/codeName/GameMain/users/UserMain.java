package codeName.GameMain.users;
import codeName.HttpClient.*;
import com.google.gson.Gson;
import DTO.GameDTO;
import DTO.TeamDTO;
import engine.GamePackage.Player;

import java.util.Arrays;
import java.util.InputMismatchException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static javafx.application.Platform.exit;

public class UserMain {
    public void userMainMenu() throws IOException {
        Scanner sc = new Scanner(System.in);
        String username = getUsername();
        String response;
        Player player = null;
        boolean newGame = false;
        int gameNumber = 0, teamNumber;
        showUserMenu();
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.println(new ShowAllGames().showAllGames());
                break;
            case 2:
                try {
                    response = new PendingGames().showPendingGames();
                    printPendingGameDetails(response);
                    if (!response.equalsIgnoreCase("{\"message\":\"No pending games\"}")) {
                        gameNumber = selectGame(sc);
                        if (gameNumber != 0) {
                            teamNumber = selectTeam(sc, gameNumber);
                            if (teamNumber != 0) {
                                String role = selectRole(sc, gameNumber, teamNumber);
                                if (!role.isEmpty()) {
                                    response = new JoinGame().joinGame(username, gameNumber, teamNumber, role);
                                    System.out.println(response);
                                    if(role.equalsIgnoreCase("Guesser"))
                                        player= new Player(username,Player.Role.GUESSER,gameNumber);
                                    else
                                        player= new Player(username,Player.Role.DEFINER,gameNumber);
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

    private static void printPendingGameDetails(String jsonResponse) {
        Gson gson = new Gson();
        GameDTO[] gamesArray = gson.fromJson(jsonResponse, GameDTO[].class);
        List<GameDTO> games = Arrays.asList(gamesArray);

        int gameIndex = 1;
        for (GameDTO game : games) {
            System.out.println("Game " + gameIndex + ":");
            System.out.println("1. Game name: " + game.getName());
            System.out.println("2. Game status: " + (game.isActive() ? "Active" : "Pending"));
            System.out.println("3. Board details: " + game.getNumRows() + "X" + game.getNumCols());
            System.out.println("4. Dictionary file name: " + game.getDictName() + ", Unique words: " + game.getGameWordsCount());
            System.out.println("5. Normal words: " + game.getGameWordsCount() + ", Black words: " + game.getBlackWordsCount());
            System.out.println("6. Teams details:");

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
            gameIndex++;
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

                // Fetch and display game details
                String gameDetails = new PendingGames().selectPendingGame(selectedGameIndex);
                System.out.println("Selected Game Details:");
                System.out.println(gameDetails);

                // Parse the JSON response to get the game serial number
                Gson gson = new Gson();
                GameDTO game = gson.fromJson(gameDetails, GameDTO.class);
                gameNumber = game.getGameSerialNumber();

                validInput = true;
            } catch (InputMismatchException e) {
                sc.nextLine(); // Clear the invalid input
                System.out.println("Invalid input. Please enter a valid game number.");
            } catch (IOException e) {
                System.out.println("Error fetching game details: " + e.getMessage());
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
                System.out.println("Selected Team Details:");
                printTeamDetails(teamDetails);

                validInput = true;
            } catch (InputMismatchException e) {
                sc.nextLine(); // Clear the invalid input
                System.out.println("Invalid input. Please enter a valid team number.");
            } catch (IOException e) {
                System.out.println("Error fetching team details: " + e.getMessage());
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
                if (roleDetails.contains("Role not available")) {
                    System.out.println("The selected role is not available. Please choose another role.");
                    continue;
                }

                System.out.println("Selected Role Details:");
                System.out.println(roleDetails);

                validInput = true;
            } catch (InputMismatchException e) {
                sc.nextLine(); // Clear the invalid input
                System.out.println("Invalid input. Please enter 1 for Definer or 2 for Guesser.");
            } catch (IOException e) {
                System.out.println("Error fetching role details: " + e.getMessage());
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

    public static void showUserMenu(){
        System.out.println("User Menu:\n");
        System.out.println("1.Show active games info\n2.Join a game!\n" +
                "3.Exit\nPlease enter your choice:");
    }
}
