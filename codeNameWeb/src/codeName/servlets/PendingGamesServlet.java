package codeName.servlets;

import engine.EnginePackage.EngineImpl;
import engine.GamePackage.AllGames;
import engine.GamePackage.Game;
import engine.GamePackage.Team;
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
        AllGames games = (AllGames) getServletContext().getAttribute("games");
        String gameNumberStr = request.getParameter("gameNumber");
        Set<Game> pendingGames;
        if(gameNumberStr == null || gameNumberStr.isEmpty()) {
            if (games == null) {
                response.getWriter().println("No pending games");
                return;
            }

            pendingGames = games.getPendingGames();
            if (pendingGames.isEmpty()) {
                response.getWriter().println("No pending games");
                return;
            }

            StringBuilder result = new StringBuilder();
            result.append("The pending games are:\n");
            int gameIndex = 1;
            for (Game game : pendingGames) {
                result.append(String.format("%d. Game Name: %s\n", gameIndex, game.getName()));
                int teamIndex = 1;
                for (Team team : game.getTeams()) {
                    result.append(String.format("  %d. Team Name: %s\n", teamIndex, team.getTeamName()));
                    result.append(String.format("     Number of words: %d\n", team.getWordsNeedToGuess().size()));
                    result.append(String.format("     Definers required: %d, Definers registered: %d\n", team.getNumOfDefiners(), team.getActiveDefiners()));
                    result.append(String.format("     Guessers required: %d, Guessers registered: %d\n", team.getNumOfGuessers(), team.getActiveGuessers()));
                    teamIndex++;
                }
                gameIndex++;
            }

            response.setContentType("text/plain");
            response.getWriter().write(result.toString());
        } else {
            try {
                int gameNumber = Integer.parseInt(gameNumberStr);
                pendingGames = games.getPendingGames();
                if (gameNumber <= 0 || gameNumber > pendingGames.size()) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().println("Game not found");
                    return;
                }
                Game chosenGame = (Game) pendingGames.toArray()[gameNumber - 1];
                EngineImpl g = new EngineImpl();
                response.getWriter().write(g.showLoadedGameInfo(chosenGame));
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Invalid game number format");
            } catch (IndexOutOfBoundsException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("Game not found");
            }
        }
    }
}
