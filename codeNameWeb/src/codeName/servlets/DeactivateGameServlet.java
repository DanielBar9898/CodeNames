package codeName.servlets;

import engine.GamePackage.App;
import engine.GamePackage.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet (name = "deactivateGame" , urlPatterns = "/deactivateGame")
public class DeactivateGameServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        App games = (App) request.getServletContext().getAttribute("allGames");
        String gameNumber = request.getParameter("gameNum");
        int gameID = Integer.parseInt(gameNumber);
        Game currentGame = games.getGameById(gameID);
        currentGame.deactivateGame();
        PrintWriter out = response.getWriter();
        out.write("The game has been deactivated.");
    }
}
