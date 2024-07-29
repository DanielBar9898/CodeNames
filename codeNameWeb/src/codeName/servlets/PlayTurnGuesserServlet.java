package codeName.servlets;

import codeName.utils.Response;
import com.google.gson.Gson;
import engine.EnginePackage.BooleanWrapper;
import engine.EnginePackage.EngineImpl;
import engine.GamePackage.App;
import engine.GamePackage.Game;
import engine.GamePackage.Team;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "playTurnGuesser", urlPatterns = "/playTurnGuesser")
public class PlayTurnGuesserServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Response res = new Response();
        App games = (App) getServletContext().getAttribute("allGames");
        if(games == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        BooleanWrapper gameOver = new BooleanWrapper(false);
        String playerName = request.getParameter("name");
        String gameNum = request.getParameter("gameNumber");
        String guess = request.getParameter("guess");
        int gameID = Integer.parseInt(gameNum);
        List<Integer> gueesesIndex = parseNumbers(guess);
        PrintWriter out = response.getWriter();
        if(playerName == null || playerName.equals("")) {
            out.write("Player name i Empty! error");
            return;
        }
        if(guess == null || guess.equals("")) {
            out.write("Guess is Empty! error");
            return;
        }
        Game currentGame = games.getGameById(gameID);
        Team teamTurn = currentGame.getCurrentTeam();
        EngineImpl engine = new EngineImpl();
        for(Integer index : gueesesIndex) {
            engine.playTurn(teamTurn,index,gameOver,res,currentGame.getGameBoard());
        }
        currentGame.nextTurn();
        if(gameOver.getValue()){
            out.write("GAME OVER!");
            return;
        }
        for(String msg : res.getMessages()) {
            out.write(msg);
        }
    }
    public static List<Integer> parseNumbers(String numbers) {
        // Split the string by commas
        String[] numberStrings = numbers.split(",");

        // Create a List to hold the integers
        List<Integer> numberList = new ArrayList<>();

        // Parse each number and add it to the list
        for (String numberString : numberStrings) {
            try {
                numberList.add(Integer.parseInt(numberString.trim()));
            } catch (NumberFormatException e) {
                // Handle the case where the string is not a valid integer
                System.err.println("Invalid number format: " + numberString);
                // Optionally, you can decide what to do in case of an error
                // For example, you can skip this number or assign a default value
                // numberList.add(0); // Uncomment to assign 0 as a default value
            }
        }
        return numberList;
    }
}
