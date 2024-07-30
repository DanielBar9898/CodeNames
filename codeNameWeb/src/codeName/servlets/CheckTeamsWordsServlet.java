package codeName.servlets;

import com.google.gson.Gson;
import engine.GamePackage.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;

@WebServlet(name = "checkTeamsWords", urlPatterns = "/checkTeamsWords")
public class CheckTeamsWordsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        App games = (App) getServletContext().getAttribute("allGames");
        String gameNumber = request.getParameter("gameNum");
        int gameID = Integer.parseInt(gameNumber);
        Game currentGame = games.getGameById(gameID);
        PrintWriter out = response.getWriter();
        Iterator<Team> iterator = currentGame.getTeams().iterator();
        Set<Definers> def;
        Set<Guessers> gues;
        while (iterator.hasNext()) {
            Team team = iterator.next();
            if (team.guessedAllWords()) {
                def = team.getDefiners();
                gues = team.getGuessers();
                for (Definers d : def) {
                    callLogoutPlayerServlet(d, out);
                }
                for (Guessers g : gues) {
                    callLogoutPlayerServlet(g, out);
                }
                out.write("Team " + team.getTeamName() + " guessed all words! They are out of the game!");
                iterator.remove(); // Safe removal
            }
        }
    }

    private void callLogoutPlayerServlet(Player player, PrintWriter out) throws IOException {
        // URL of the LogoutPlayerServlet
        URL url = new URL("http://localhost:8080/codeNameWeb_Web_exploded/logoutPlayer"); // Change "yourapp" to your actual context path
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

        // Convert the player object to JSON
        Gson gson = new Gson();
        String jsonInputString = gson.toJson(player);

        // Send the JSON as the POST body
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Get the response code
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            out.write("Player " + player.getName() + " logged out successfully.\n");
        } else {
            out.write("Failed to log out player " + player.getName() + ". Response code: " + responseCode + "\n");
        }
    }
}
