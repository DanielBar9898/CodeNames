package codeName.servlets.UserGameServlets;

import engine.GamePackage.App;
import engine.GamePackage.Game;
import engine.GamePackage.Team;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet (name = "nextTurn" , urlPatterns = "/nextTurn")
public class GetNextTurnServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        App games = (App) request.getServletContext().getAttribute("allGames");
        String team = request.getParameter("teamName");
        Team t = null;
        if (games == null) {
            response.getWriter().write("No games available");
            return;
        }
        for(Game game : games.getGames()) {
            t = game.getTeamByName(team);
        }
        if(t == null) {
            response.getWriter().write("No such team");
        }
        else{
            response.getWriter().write(t.getNextRoleToPlay());
        }
    }
}
