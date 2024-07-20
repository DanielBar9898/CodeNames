package codeName.servlets;

import engine.EnginePackage.EngineImpl;
import engine.GamePackage.AllGames;
import engine.GamePackage.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
@WebServlet(name = "loadXml" , urlPatterns = "/loadXmlFile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class LoadXMLServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Part filePart = request.getPart("filePath");
        String fileName = filePart.getSubmittedFileName();
        EngineImpl engine = new EngineImpl();
        AllGames allGames = (AllGames) getServletContext().getAttribute("allGames");
        if (allGames == null) {
            allGames = new AllGames();
            getServletContext().setAttribute("allGames", allGames);
        }


        Game currentGame = engine.loadXmlFile(fileName);

        if(currentGame == null) {
            response.getWriter().write("XML file could not be loaded.");
            return;
        }
        else if(!allGames.isGameNameUnique(currentGame.getName())){
            response.getWriter().write("The game name is already in use.");
            return;
        }
        if (currentGame.validateFile(response.getWriter())) {
            allGames.addGame(currentGame);
            getServletContext().setAttribute("game", currentGame);
            response.getWriter().write("XML file loaded and validated successfully.");
        }
    }
}
