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
        int guessIndex = Integer.parseInt(guess);
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
        ArrayList<Team> teams = currentGame.getTeams();
        Team teamTurn = currentGame.getCurrentTeam();
        EngineImpl engine = new EngineImpl();
        engine.playTurn(teamTurn,guessIndex,gameOver,res,currentGame.getGameBoard(),teams);
        teamTurn.makeTurn();
        currentGame.nextTurn();
        if(gameOver.getValue()){
            out.write("GAME OVER FOR YOUR TEAM!");
            return;
        }
        for(String msg : res.getMessages()) {
            out.write(msg);
        }
    }
}
