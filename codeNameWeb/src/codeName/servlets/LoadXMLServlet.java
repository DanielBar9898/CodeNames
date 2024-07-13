package codeName.servlets;

import engine.*;
import codeName.constants.Constants;
import codeName.utils.ServletUtils;
import codeName.utils.SessionUtils;
import engine.EnginePackage.Engine;
import engine.EnginePackage.EngineImpl;
import engine.GamePackage.Game;
import engine.users.UserManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoadXMLServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EngineImpl engine = new EngineImpl();
        Game currentGame = engine.loadXmlFile(request.getReader().readLine());
        getServletContext().setAttribute("game", currentGame);
        currentGame.validateFile();
    }
}
