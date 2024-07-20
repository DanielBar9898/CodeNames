package codeName.HttpClient;

import java.io.IOException;
import java.util.Scanner;


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
                System.out.println(new ShowAllGames().showAllGames());
                break;

            case 3:
            response = new ActiveGames().showActiveGames();
                System.out.println(response);
            if(!response.equalsIgnoreCase("No active games")) {
                System.out.println("Please select the game you want to watch (Enter a number):");
                sc.nextLine();
                gameNumber = sc.nextInt();
                System.out.println(new ActiveGames().selectActiveGame(gameNumber));
            }
            break;


        }
    }

    public static void showAdminMenu(){
        System.out.println("Admin Menu:\n");
        System.out.println("1.Load XML+TXT file\n2.Show active games info\n" +
                "3.Watch an active game\n4.Exit\nPlease enter your choice");
    }
}
