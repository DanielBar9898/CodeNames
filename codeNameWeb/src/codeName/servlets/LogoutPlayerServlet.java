package codeName.servlets;

import codeName.utils.ServletUtils;
import engine.GamePackage.App;
import engine.GamePackage.Player;
import engine.GamePackage.UserManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import com.google.gson.Gson;

@WebServlet(name = "LogoutPlayerServlet", urlPatterns = "/logoutPlayer")
public class LogoutPlayerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        Player player = gson.fromJson(request.getReader(), Player.class);

        if (player == null || player.getName() == null || player.getName().isEmpty()) {
           // response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Player data or name is required");
            return;
        }

        String playerName = player.getName();
        App app = (App) getServletContext().getAttribute("allGames");
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        synchronized (app) {
            if (userManager.isUserExists(playerName)) {
                app.removePlayer(player);
                userManager.removeUser(playerName);
              //  response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Player logged out and removed from games successfully");
            } else {
              //  response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Player not found");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "LogoutPlayerServlet";
    }
}
