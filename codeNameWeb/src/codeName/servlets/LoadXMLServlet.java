package codeName.servlets;

import engine.EnginePackage.EngineImpl;
import engine.GamePackage.AllGames;
import engine.GamePackage.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoadXMLServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        EngineImpl engine = new EngineImpl();
        AllGames allGames = (AllGames) getServletContext().getAttribute("allGames");
        if (allGames == null) {
            allGames = new AllGames();
            getServletContext().setAttribute("allGames", allGames);
        }

        String filePath = request.getParameter("filePath");
        String gameName = request.getParameter("gameName");

        Game currentGame = engine.loadXmlFile(filePath);

        if (currentGame != null && currentGame.validateFile()) {
            allGames.addGame(currentGame);
            getServletContext().setAttribute("game", currentGame);
            response.getWriter().write("XML file loaded and validated successfully.");
        } else {
            response.getWriter().write("Failed to load or validate the XML file.");
        }
    }
}
