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
            res.addMessage("Player name cannot be empty");
            jsonResponse = gson.toJson(res);
            out.write(jsonResponse);
            return;
        }
        if(hint == null || hint.equals("")) {
            res.addMessage("Hint cannot be empty");
            jsonResponse = gson.toJson(res);
            out.write(jsonResponse);
            return;
        }
        Game currentGame = games.getGameById(gameID);
        Team teamTurn = currentGame.getNextTeam();
        if(!teamTurn.teamMember(playerName)){
            res.addMessage("You are not in the playing team");
            jsonResponse = gson.toJson(res);
            out.write(jsonResponse);
            return;
        }
        EngineImpl engine = (EngineImpl) getServletContext().getAttribute("engine");
        if(engine == null) {
            EngineImpl e = new EngineImpl();
            engine = e;
            getServletContext().setAttribute("engine", e);
        }
        engine.playTurn(teamTurn , hint ,numOfWords , res);
        jsonResponse = gson.toJson(res);
        out.write(jsonResponse);
    }
}
