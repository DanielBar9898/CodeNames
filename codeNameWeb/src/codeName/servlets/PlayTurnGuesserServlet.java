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

@WebServlet(name = "playTurn" , urlPatterns = "/playTurnGuesser")
public class PlayTurnGuesserServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Response res = new Response();
        Gson gson = new Gson();
        String jsonResponse;
        App games = (App) getServletContext().getAttribute("games");
        if(games == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        BooleanWrapper gameOver = new BooleanWrapper(false);
        String playerName = request.getParameter("name");
        String gameNum = request.getParameter("gameNumber");
        String guess = request.getParameter("guess");
        int gameID = Integer.parseInt(gameNum);
        int guessID = Integer.parseInt(guess);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if(playerName == null || playerName.equals("")) {
            res.addMessage("Player name cannot be empty");
            jsonResponse = gson.toJson(res);
            out.write(jsonResponse);
            return;
        }
        if(guess == null || guess.equals("")) {
            res.addMessage("Guess cannot be empty");
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
        }
        EngineImpl engine = (EngineImpl) getServletContext().getAttribute("engine");
        if(engine == null) {
            EngineImpl e = new EngineImpl();
            engine = e;
            getServletContext().setAttribute("engine", e);
        }
        engine.playTurn(teamTurn,guessID,gameOver , res);
        jsonResponse = gson.toJson(res);
        out.write(jsonResponse);
    }
}
