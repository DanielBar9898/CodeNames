package codeName.servlets;

import DTO.GameDTO;
import codeName.utils.ServletUtils;
import com.google.gson.Gson;
import DTO.GameStatusDTO;
import engine.GamePackage.App;
import engine.GamePackage.Game;
import engine.GamePackage.Team;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@WebServlet(name = "gameStatus", urlPatterns = "/gameStatus")
public class GameStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String gameNumberStr = request.getParameter("gameNumber");

        if (gameNumberStr == null || gameNumberStr.isEmpty()) {
            response.getWriter().write("{\"error\": \"Game number is required\"}");
            return;
        }

        App games = (App) getServletContext().getAttribute("games");
        if (games == null) {
            response.getWriter().write("{\"error\": \"No games found\"}");
            return;
        }

        try {
            int gameNumber = Integer.parseInt(gameNumberStr);
            Game game = games.getGameById(gameNumber);
            if (game == null) {
                response.getWriter().write("{\"error\": \"Game not found\"}");
                return;
            }

            Team currentTeam = game.getCurrentTeam();
            Team nextTeam = game.getNextTeam();

            GameStatusDTO gameStatusDTO = new GameStatusDTO(
                    game.isActive() ? "Active" : "Pending",
                    currentTeam.getTeamName(),
                    currentTeam.getWordsGuessed() + "/" + currentTeam.getWordsToGuess(),
                    currentTeam.getNumOfTurns(),
                    nextTeam != null ? nextTeam.getTeamName() : "None"
            );

            String json = new Gson().toJson(gameStatusDTO);
            response.getWriter().write(json);

        } catch (NumberFormatException e) {
            response.getWriter().write("{\"error\": \"Invalid game number format\"}");
        }
    }

    private static int extractGameSerialNumber(String jsonResponse) {
        Gson gson = new Gson();
        GameDTO gameDTO = gson.fromJson(jsonResponse, GameDTO.class);
        return gameDTO.getGameSerialNumber();
    }
}
