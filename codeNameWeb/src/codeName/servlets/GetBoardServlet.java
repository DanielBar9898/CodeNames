package codeName.servlets;

import codeName.utils.ServletUtils;
import com.google.gson.Gson;
import DTO.BoardDTO;
import engine.GamePackage.App;
import engine.GamePackage.Board;
import engine.GamePackage.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "getBoard", urlPatterns = "/getBoard")
public class GetBoardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String gameNumberStr = request.getParameter("gameNumber");

        if (gameNumberStr == null || gameNumberStr.isEmpty()) {
            response.getWriter().write("{\"error\": \"Game number is required\"}");
            return;
        }

        App games = (App) getServletContext().getAttribute("games");
        if (games == null) {
            response.getWriter().write("{\"error\": \"No games found\"}");
            return;
        }

        try {
            int gameNumber = Integer.parseInt(gameNumberStr);
            Game game = games.getGameById(gameNumber);
            if (game == null) {
                response.getWriter().write("{\"error\": \"Game not found\"}");
                return;
            }

            BoardDTO boardDTO = ServletUtils.convertBoardToDTO(game.getGameBoard());
            String json = new Gson().toJson(boardDTO);
            response.getWriter().write(json);

        } catch (NumberFormatException e) {
            response.getWriter().write("{\"error\": \"Invalid game number format\"}");
        }
    }
}
