package codeName.servlets.GamesInfoServlets;

import codeName.utils.ServletUtils;
import engine.GamePackage.App;
import engine.GamePackage.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@WebServlet(name = "showGamesInfo", urlPatterns = "/gamesInfo")
public class ShowGamesInfoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        App games = (App) getServletContext().getAttribute("allGames");
        if (games == null || games.getGames().isEmpty()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"message\":\"No games available\"}");
            return;
        }

        Set<Game> gameSet = games.getGames();

        String json = ServletUtils.convertGamesToJson(gameSet);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
