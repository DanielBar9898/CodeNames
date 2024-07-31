package codeName.servlets.UserGameServlets;

import codeName.utils.Response;
import engine.EnginePackage.EngineImpl;
import engine.GamePackage.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "playTurn" , urlPatterns = "/playTurnDefiner")
public class PlayTurnDefinerServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Response res = new Response();
        App games = (App) getServletContext().getAttribute("allGames");
        if(games == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String playerName = request.getParameter("name");
        String gameNum = request.getParameter("gameNumber");
        String hint = request.getParameter("hint");
        String numWords = request.getParameter("numOfWords");
        int numOfWords = Integer.parseInt(numWords);
        int gameID = Integer.parseInt(gameNum);
        PrintWriter out = response.getWriter();
        if(playerName == null || playerName.equals("")) {
            out.write("Player name i Empty! error");
            return;
        }
        if(hint == null || hint.equals("")) {
            out.write("Hint is Empty! error");
            return;
        }
        Game currentGame = games.getGameById(gameID);
        Team teamTurn = currentGame.getCurrentTeam();
        EngineImpl engine = new EngineImpl();
        engine.playTurn(teamTurn , hint ,numOfWords , res);
        out.write("Player " + playerName + " turn completed successfully!");
    }
}
