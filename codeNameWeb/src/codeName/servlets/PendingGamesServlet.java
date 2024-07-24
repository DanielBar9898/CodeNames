package codeName.servlets;


import codeName.utils.ServletUtils;
import engine.EnginePackage.EngineImpl;
import engine.GamePackage.App;
import engine.GamePackage.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@WebServlet(name = "showPendingGame", urlPatterns = "/pendingGame")
public class PendingGamesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        App games = (App) getServletContext().getAttribute("games");
        String gameNumberStr = request.getParameter("gameNumber");
        Set<Game> pendingGames;
        if(gameNumberStr == null || gameNumberStr.isEmpty()) {
            if (games == null) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"message\":\"No pending games\"}");
                return;
            }

            pendingGames = games.getPendingGames();
            if (pendingGames.isEmpty()) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"message\":\"No pending games\"}");
                return;
            }

            String json = ServletUtils.convertGamesToJson(pendingGames);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } else {
            try {
                int gameNumber = Integer.parseInt(gameNumberStr);
                pendingGames = games.getPendingGames();
                if (gameNumber <= 0 || gameNumber > pendingGames.size()) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().println("{\"message\":\"Game not found\"}");
                    return;
                }
                Game chosenGame = (Game) pendingGames.toArray()[gameNumber - 1];
                EngineImpl g = new EngineImpl();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(g.showLoadedGameInfo(chosenGame));
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("{\"message\":\"Invalid game number format\"}");
            } catch (IndexOutOfBoundsException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("{\"message\":\"Game not found\"}");
            }
        }
    }
}
