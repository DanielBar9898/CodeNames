package codeName.servlets;

import engine.GamePackage.App;
import engine.GamePackage.Game;
import engine.GamePackage.Team;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "PlayingTeamTurn" , urlPatterns = "/playingTurn")
public class PlayingTeamServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        App games = (App) getServletContext().getAttribute("allGames");
        if(games == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String gameNumber = request.getParameter("gameID");
        int gameID = Integer.parseInt(gameNumber);
        Game currentGame = games.getGameById(gameID);
        Team teamTurn = currentGame.getCurrentTeam();
        response.getWriter().write(teamTurn.getTeamName());
    }

}
