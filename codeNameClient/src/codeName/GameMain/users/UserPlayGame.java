package codeName.GameMain.users;

import DTO.BoardDTO;
import DTO.GameStatusDTO;
import DTO.WordDTO;
import codeName.HttpClient.*;
import com.google.gson.Gson;
import engine.GamePackage.Board;
import engine.GamePackage.Player;
import engine.GamePackage.Word;

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UserPlayGame {
    public void userGameMenu(Player player) throws IOException {
        showUserPlayGameMenu();
        boolean Hidden=true,Visible=false;
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if(player == null){
            choice=3;
        }
        switch(choice){
            case 1:

                String gameStatusJson = new GameStatus().getGameStatus(player.getSerialGameNumber());
                Gson gson = new Gson();
                GameStatusDTO gameStatus = gson.fromJson(gameStatusJson, GameStatusDTO.class);

                printGameStatus(gameStatus);
                displayBoard(player, gson);

        }

    }
    public static void showUserPlayGameMenu(){
        System.out.println("Welcome!\n");
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
            word.setColor(Word.cardColor.valueOf(wordDTO.getColor()));
            word.setSerialNumber(wordDTO.getSerialNumber());
            if (wordDTO.isFound()) {
                word.found();
            }
            words.add(word);
        }

        Board board = new Board(boardDTO.getNumRows(), boardDTO.getNumCols(), boardDTO.getNumOfBlackWords(), boardDTO.getNumOfTotalWords(), words);
        return board;
    }
}
