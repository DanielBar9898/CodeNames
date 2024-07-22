package codeName.GameMain.users;

public class UserPlayGame {
    public void userGameMenu(int gameNumber){
        showUserPlayGameMenu();

    }
    public static void showUserPlayGameMenu(){
        System.out.println("Welcome!\n");
        System.out.println("Game Menu:\n");
        System.out.println("1.Show status game \n2.Play turn!\n" +
                "3.Exit\nPlease enter your choice:");
    }
}
