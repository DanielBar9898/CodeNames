package codeName.servlets;

import engine.GamePackage.AllGames;
import engine.GamePackage.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@WebServlet(name = "showGamesInfo" , urlPatterns = "/gamesInfo")

public class ShowGamesInfoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AllGames Games = (AllGames) getServletContext().getAttribute("allGames");
        if (Games == null) {
            response.getWriter().write("No games available");
            return;
        }

        Set<Game> games = Games.getGames(); // Assuming getGames() returns a Set<Game>

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        StringBuilder result = new StringBuilder();
        for (Game game : games) {
            result.append(game.toString()).append("\n");
        }

        response.getWriter().write(result.toString());
    }
}

