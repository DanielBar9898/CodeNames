package codeName.servlets;

import codeName.utils.ServletUtils;
import com.google.gson.Gson;
import DTO.GameDTO;
import engine.GamePackage.App;
import engine.GamePackage.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet(name = "showActiveGame", urlPatterns = "/activeGame")
public class ShowActiveGameServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String gameNumberStr = request.getParameter("gameNumber");
        App games = (App) getServletContext().getAttribute("games");

        if (games == null) {
            response.getWriter().write("{\"error\": \"No games found\"}");
            return;
        }

        if (gameNumberStr == null || gameNumberStr.isEmpty()) {
            Set<Game> activeGames = games.getActiveGames();
            if (activeGames.isEmpty()) {
                response.getWriter().write("{\"error\": \"No active games\"}");
                return;
            }

            List<GameDTO> activeGameDTOs = new ArrayList<>();
            int gameActiveSerialNumber = 1;
            for (Game game : activeGames) {
                GameDTO gameDTO = ServletUtils.convertGameToDTO(game);
                gameDTO.setGameSerialNumber(gameActiveSerialNumber);
                activeGameDTOs.add(gameDTO);
                gameActiveSerialNumber++;
            }

            String json = new Gson().toJson(activeGameDTOs);
            response.getWriter().write(json);

        } else {
            try {
                int gameNumber = Integer.parseInt(gameNumberStr);
                Game activeGame = games.getGameById(gameNumber);
                if (activeGame == null) {
                    response.getWriter().write("{\"error\": \"Game not found\"}");
                    return;
                }

                String json = new Gson().toJson(ServletUtils.convertGameToDTO(activeGame));
                response.getWriter().write(json);

            } catch (NumberFormatException e) {
                response.getWriter().write("{\"error\": \"Invalid game number format\"}");
            }
        }
    }
}
