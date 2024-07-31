package codeName.servlets.UserGameServlets;

import codeName.utils.ServletUtils;
import engine.GamePackage.App;
import engine.GamePackage.Game;
import engine.GamePackage.Team;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "selectTeam", urlPatterns = "/selectTeam")
public class SelectTeamServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        App games = (App) getServletContext().getAttribute("allGames");
        String gameNumberStr = request.getParameter("gameNumber");
        String teamNumberStr = request.getParameter("teamNumber");

        if (games == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"error\": \"No games found\"}");
            return;
        }

        if (gameNumberStr == null || gameNumberStr.isEmpty() || teamNumberStr == null || teamNumberStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Game number and team number are required\"}");
            return;
        }

        try {
            int gameNumber = Integer.parseInt(gameNumberStr);
            int teamNumber = Integer.parseInt(teamNumberStr);

            Game selectedGame = games.getGameById(gameNumber);
            if (selectedGame == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"Game not found\"}");
                return;
            }

            Team selectedTeam = (Team) selectedGame.getTeams().toArray()[teamNumber - 1];
            if (selectedTeam == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"Team not found\"}");
                return;
            }
            if ((selectedTeam.getActiveGuessers()== selectedTeam.getNumOfGuessers())&& (selectedTeam.getActiveDefiners()== selectedTeam.getNumOfDefiners())) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"This team is already Full\"}");
                return;
            }

            String json = ServletUtils.convertTeamToJson(selectedTeam);
            response.getWriter().write(json);

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid game number or team number format\"}");
        } catch (IndexOutOfBoundsException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"Team not found\"}");
        }
    }
}
