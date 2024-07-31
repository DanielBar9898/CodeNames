package codeName.servlets.AdminServlets;

import engine.EnginePackage.EngineImpl;
import engine.GamePackage.App;
import engine.GamePackage.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "loadXml" , urlPatterns = "/loadXmlFile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class LoadXMLServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Part filePathPart = request.getPart("filePath");
        String filePath = extractString(filePathPart);
        EngineImpl engine = new EngineImpl();
        String content = readFileContent(filePath);
        String txtName = extractDictionaryFile(content);
        App allGames = (App) getServletContext().getAttribute("allGames");
        if (allGames == null) {
            allGames = new App();
            getServletContext().setAttribute("allGames", allGames);
        }
        Game currentGame = engine.loadXmlFile(filePath,txtName,response.getWriter());
        if(currentGame == null) {
            response.getWriter().write("XML file could not be loaded.");
            return;
        }
        else if(!allGames.isGameNameUnique(currentGame.getName())){
            response.getWriter().write("The game name is already in use.");
            return;
        }
        String txtPath = replaceFileName(filePath , currentGame.getDictName());
        currentGame.extractWordsFromFile(new File(txtPath));
        currentGame.getGameBoard().assignWordsToTeams(currentGame.getTeams());
        currentGame.setBlackWords();
        currentGame.setFileName(filePath);
        if (currentGame.validateFile(response.getWriter())) {
            //allGames.setAdmin(true);
            allGames.addGame(currentGame);
            getServletContext().setAttribute("game", currentGame);
            response.getWriter().write("XML file loaded and validated successfully.");
        }
    }
    private synchronized String extractString(Part part) throws IOException {
        InputStream inputStream = part.getInputStream();
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }
    private static String replaceFileName(String filePath, String newFileName) {
        // Find the last occurrence of the file separator
        int lastSeparatorIndex = filePath.lastIndexOf(File.separator);

        if (lastSeparatorIndex == -1) {
            // If no separator is found, return the newFileName only
            return newFileName;
        }

        // Create the new file path by combining the path up to the last separator with the new file name
        return filePath.substring(0, lastSeparatorIndex + 1) + newFileName;
    }
    public static String readFileContent(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        // Use try-with-resources to ensure the reader is closed properly
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }

        return content.toString();
    }
    public static String extractDictionaryFile(String xmlContent) {
        String patternString = "<ECN-Dictionary-File>(.*?)</ECN-Dictionary-File>";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(xmlContent);

        if (matcher.find()) {
            return matcher.group(1).trim();
        } else {
            return "";
        }
    }
}
