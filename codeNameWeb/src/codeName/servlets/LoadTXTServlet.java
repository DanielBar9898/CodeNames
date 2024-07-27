package codeName.servlets;

import engine.GamePackage.App;
import engine.GamePackage.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

@WebServlet (name = "loadTxt" , urlPatterns = "/loadTxtFile")

public class LoadTXTServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Game currentGame = (Game) getServletContext().getAttribute("allGames");
        if (currentGame == null) {
            response.getWriter().write("No game is currently loaded.");
            return;
        }

        String filePath = request.getParameter("filePath");
        File txtFile = new File(filePath);

        if (!txtFile.exists() || !txtFile.isFile()) {
            response.getWriter().write("Invalid TXT file path.");
            return;
        }

        try {
            currentGame.extractWordsFromFile(txtFile);
            App games = (App) getServletContext().getAttribute("games");
            games.addGame(currentGame);
            response.getWriter().write("TXT file loaded successfully.");
        } catch (IOException e) {
            response.getWriter().write("Failed to load the TXT file: " + e.getMessage());
        }
    }
}
