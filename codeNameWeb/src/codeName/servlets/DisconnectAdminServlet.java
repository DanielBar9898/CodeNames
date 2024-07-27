package codeName.servlets;

import engine.GamePackage.App;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet (name = "disconnect" , urlPatterns = "/disconnect")
public class DisconnectAdminServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        App allGames = (App) request.getServletContext().getAttribute("allGames");
        if (allGames != null) {
            allGames.setAdmin(false);
            response.getWriter().write("Disconnected from Admin!");
            return;
        }
        response.getWriter().write("There was no Admin connected to the game!");
    }
}
