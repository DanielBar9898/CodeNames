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
        int guessID = Integer.parseInt(guess);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
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
        Team teamTurn = currentGame.getNextTeam();
        if(!teamTurn.teamMember(playerName)){
            out.write("Player " + playerName + " is not in the playing team");
            return;
        }
        EngineImpl engine = new EngineImpl();
        engine.playTurn(teamTurn,guessID,gameOver,res);
        if(gameOver.getValue()){
            out.write("GAME OVER!");
            return;
        }
        for(String msg : res.getMessages()) {
            out.write(msg);
        }
    }
}
