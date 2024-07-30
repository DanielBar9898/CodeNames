package codeName.servlets;

import engine.GamePackage.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet (name = "teamInfo" , urlPatterns = "/teamInfo")
public class ShowTeamInfoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        App games = (App) getServletContext().getAttribute("allGames");
        String teamName = request.getParameter("teamName");
        String gameId = request.getParameter("gameID");
        int gameID = Integer.parseInt(gameId);
        Game currentGame = games.getGameById(gameID);
        Team team = currentGame.getTeamByName(teamName);
        PrintWriter out = response.getWriter();
        out.write("So far "+teamName+" have guessed "+team.getWordsGuessed()+"/"+
                team.getWordsToGuess()+ " words!");
    }
}
