package codeName.servlets;

import engine.GamePackage.App;
import engine.GamePackage.Game;
import engine.GamePackage.Team;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "selectRole", urlPatterns = "/selectRole")
public class SelectRoleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        App games = (App) getServletContext().getAttribute("allGames");
        String gameNumberStr = request.getParameter("gameNumber");
        String teamNumberStr = request.getParameter("teamNumber");
        String role = request.getParameter("role");

        if (games == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"error\": \"No games found\"}");
            return;
        }

        if (gameNumberStr == null || gameNumberStr.isEmpty() || teamNumberStr == null || teamNumberStr.isEmpty() || role == null || role.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Game number, team number, and role are required\"}");
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


            boolean roleAvailable = false;
            if (role.equalsIgnoreCase("Definer") && selectedTeam.getActiveDefiners() < selectedTeam.getNumOfDefiners()) {
                roleAvailable = true;
            } else if (role.equalsIgnoreCase("Guesser") && selectedTeam.getActiveGuessers() < selectedTeam.getNumOfGuessers()) {
                roleAvailable = true;
            }

            if (!roleAvailable) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"Role not available\"}");
                return;
            }

            String json = new Gson().toJson(selectedTeam);
            out.print(json);
            out.flush();

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Invalid game number or team number format\"}");
        } catch (IndexOutOfBoundsException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"error\": \"Team not found\"}");
        }
    }
}
