package codeName.servlets;

import engine.GamePackage.App;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserNameListServlet", urlPatterns = "/UserNameList")
public class UserNameListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String action = request.getParameter("action");
        App app = (App) getServletContext().getAttribute("allGames");

        if (username == null || username.isEmpty() || action == null || action.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Username and action are required\"}");
            return;
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if ("add".equalsIgnoreCase(action)) {
            if (app.getUserNames().contains(username)) {
                out.print("{\"message\": \"Username already exists\"}");
            } else {
                app.addUserName(username);
                out.print("{\"message\": \"Username added successfully\"}");
            }
        } else if ("remove".equalsIgnoreCase(action)) {
            if (app.getUserNames().contains(username)) {
                app.removeUserName(username);
                out.print("{\"message\": \"Username removed successfully\"}");
            } else {
                out.print("{\"message\": \"Username not found\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Invalid action\"}");
        }
        out.flush();
    }
}
