package codeName.servlets.AdminServlets;

import DTO.AdminDTO;
import engine.GamePackage.App;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import java.io.IOException;

@WebServlet(name = "Admin", urlPatterns = "/admin")
public class AdminLogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        App app = (App) getServletContext().getAttribute("allGames");
        AdminDTO adminDTO = new AdminDTO(app.isHasAdmin(), app.getAdminName());
        String json = new Gson().toJson(adminDTO);

        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        App allGames = (App) getServletContext().getAttribute("allGames");
        if (allGames == null) {
            allGames = new App();
            getServletContext().setAttribute("allGames", allGames);
        }
        String action = request.getParameter("action");
        String adminName = request.getParameter("adminName");

        if ("login".equals(action)) {
            if (allGames.loginAdmin(adminName)) {
             //   response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Admin logged in successfully");
            } else {
              //  response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("An admin is already logged in, please try again later.");
            }
        } else if ("logout".equals(action)) {
            if (allGames.logoutAdmin()) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Admin logged out successfully");
            } else {
            //    response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("No admin is logged in");
            }
        } else {
          //  response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid action");
        }
    }

    @Override
    public String getServletInfo() {
        return "Admin management servlet";
    }
}
