package codeName.servlets;

import codeName.utils.Response;
import com.google.gson.Gson;
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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        Response res = new Response();
        String jsonResponse;
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
        Team teamTurn = currentGame.getNextTeam();
        if(!teamTurn.teamMember(playerName)){
            out.write("Player " + playerName + " is not in the current playing team");
            return;
        }
        EngineImpl engine = new EngineImpl();
        engine.playTurn(teamTurn , hint ,numOfWords , res);
        for(String msg : res.getMessages()) {
            out.write(msg);
        }
    }
}
