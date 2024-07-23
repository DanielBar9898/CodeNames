package codeName.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import engine.GamePackage.AllGames;
import engine.GamePackage.Game;

import java.io.IOException;

@WebServlet(name = "getSerialGameNumber", urlPatterns = "/getSerialGameNumber")
public class getSerialGameNumberServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AllGames games = (AllGames) getServletContext().getAttribute("games");
        int gameIndex = Integer.parseInt(request.getParameter("gameNumber"));

        Game chosenGame = (Game) games.getPendingGames().toArray()[gameIndex - 1];

        response.setContentType("text/plain");
        response.getWriter().write(String.valueOf(chosenGame.getGameSerialNumber()));
    }
}
