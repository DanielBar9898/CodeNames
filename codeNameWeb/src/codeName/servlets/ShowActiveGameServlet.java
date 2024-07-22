package codeName.servlets;

import engine.EnginePackage.EngineImpl;
import engine.GamePackage.AllGames;
import engine.GamePackage.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@WebServlet(name = "showActiveGame" , urlPatterns = "/activeGame")

public class ShowActiveGameServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String gameNumberStr = request.getParameter("gameNumber");
        if(gameNumberStr == null || gameNumberStr.isEmpty()){
            AllGames games = (AllGames) getServletContext().getAttribute("games");
            int gameActiveSerialNumber=0;
            if(games == null) {
                response.getWriter().println("No active games");
                return;
            }
            Set<Game> activeGames = games.getActiveGames();
            if(activeGames.isEmpty()) {
                response.getWriter().println("No active games");
                return;
            }

            StringBuilder result = new StringBuilder();
            result.append("The active games are:  \n");
            for(Game game : activeGames) {
                result.append("Game number " + gameActiveSerialNumber + ": " + game.getName() + "\n");
                gameActiveSerialNumber++;

                // we need to
            }
        }
        else{
            AllGames games = (AllGames) getServletContext().getAttribute("games");
            Game activeGame = games.getGameById(Integer.parseInt(gameNumberStr));
            EngineImpl g = new EngineImpl();
            response.getWriter().write(g.showLoadedGameInfo(activeGame));
        }
    }
}
