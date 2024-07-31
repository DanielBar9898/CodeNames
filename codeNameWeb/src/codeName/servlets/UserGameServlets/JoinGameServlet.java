package codeName.servlets.UserGameServlets;

import engine.GamePackage.App;
import engine.GamePackage.Game;
import engine.GamePackage.Team;
import engine.GamePackage.Player;
import engine.GamePackage.Player.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "joinGame", urlPatterns = "/joinGame")
public class JoinGameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        App games = (App) getServletContext().getAttribute("allGames");
        String gameNumberStr = request.getParameter("gameNumber");
        String teamNumberStr = request.getParameter("teamNumber");
        String roleStr = request.getParameter("role");
        String username = request.getParameter("username");

        if (games == null || gameNumberStr == null || teamNumberStr == null || roleStr == null || username == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("All parameters (gameNumber, teamNumber, role, username) are required.");
            return;
        }

        try {
            int gameNumber = Integer.parseInt(gameNumberStr);
            int teamNumber = Integer.parseInt(teamNumberStr);

            Game selectedGame = games.getGameById(gameNumber);
            if (selectedGame == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("Game not found.");
                return;
            }

            Team selectedTeam = (Team) selectedGame.getTeams().toArray()[teamNumber - 1];
            if (selectedTeam == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("Team not found.");
                return;
            }

            Role role;
            try {
                role = Role.valueOf(roleStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("Invalid role.");
                return;
            }

            Player player = new Player(username, role,gameNumber,selectedTeam.getTeamName());
            selectedTeam.addPlayerToTeam(player);
            selectedGame.checkAndActivateGame();

            out.print("User " + username + " has joined team " + selectedTeam.getTeamName() + " as a " + role);

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("Invalid game number or team number format.");
        }
    }
}
