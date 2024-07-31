package codeName.servlets;

import engine.GamePackage.App;
import engine.GamePackage.Game;
import engine.GamePackage.Team;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet (name = "CheckTeams" , urlPatterns = "/checkAmountOfTeams")
public class CheckTeamsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        App games = (App) request.getServletContext().getAttribute("allGames");
        String gameNumber = request.getParameter("gameNum");
        int gameID = Integer.parseInt(gameNumber);
        Game currentGame = games.getGameById(gameID);
        PrintWriter out = response.getWriter();
        int numofTeams = currentGame.getTeams().size();
        for(Team t : currentGame.getTeams()) {
            if(!t.isActive()){
                numofTeams--;
            }
        }
        if(numofTeams <= 1){
            out.write( "Not enough teams in the game!" );
        }
    }
}
