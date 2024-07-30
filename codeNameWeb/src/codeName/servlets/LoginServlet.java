package codeName.servlets;

import codeName.utils.ServletUtils;
import engine.GamePackage.UserManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "Login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String username = request.getParameter("username");

        if (username != null && !username.isEmpty()) {
            username = username.trim();
            synchronized (userManager) {
                if (!userManager.isUserExists(username)) {
                    userManager.addUser(username);
                   // response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("Username added successfully");
                } else {
                    //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Username already exists. Please enter a different username.");
                }
            }
        } else {
           // response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Username cannot be null or empty.");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
