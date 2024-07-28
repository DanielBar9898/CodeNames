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

@WebServlet(name = "showPendingGame", urlPatterns = "/pendingGame")
public class PendingGamesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String gameNumberStr = request.getParameter("gameNumber");
        App games = (App) getServletContext().getAttribute("allGames");

        if (games == null) {
            response.getWriter().write("{\"error\": \"No games found\"}");
            return;
        }
        Set<Game> pendingGames = games.getPendingGames();
        if (gameNumberStr == null || gameNumberStr.isEmpty()) {
         //   Set<Game> pendingGames = games.getPendingGames();
            if (pendingGames.isEmpty()) {
                response.getWriter().write("{\"error\": \"No pending games\"}");
                return;
            }

            List<GameDTO> pendingGameDTOs = new ArrayList<>();
            //int gamePendingSerialNumber = 1;
            for (Game game : pendingGames) {
                GameDTO gameDTO = ServletUtils.convertGameToDTO(game);
                //gameDTO.setGameSerialNumber(gamePendingSerialNumber);
                pendingGameDTOs.add(gameDTO);
                //gamePendingSerialNumber++;
            }

            String json = new Gson().toJson(pendingGameDTOs);
            response.getWriter().write(json);

        } else {
            try {
                int gameNumber = Integer.parseInt(gameNumberStr);
                Game pendingGame = games.getGameById(gameNumber);
                if (pendingGame == null) {
                    response.getWriter().write("{\"error\": \"Game not found\"}");
                    return;
                }

                String json = new Gson().toJson(ServletUtils.convertGameToDTO(pendingGame));
                response.getWriter().write(json);

            } catch (NumberFormatException e) {
                response.getWriter().write("{\"error\": \"Invalid game number format\"}");
            }
        }
    }
}
