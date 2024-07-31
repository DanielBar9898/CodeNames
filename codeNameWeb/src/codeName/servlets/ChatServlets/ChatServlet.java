package codeName.servlets.ChatServlets;

import com.google.gson.Gson;
import engine.chatPackage.ChatManager;
import engine.chatPackage.SingleChatEntry;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ChatServlet", urlPatterns = "/chat")
public class ChatServlet extends HttpServlet {

    private static final String ACTION_ADD_TEAM_MESSAGE = "addTeamMessage";
    private static final String ACTION_ADD_DEFINER_MESSAGE = "addDefinerMessage";
    private static final String ACTION_GET_TEAM_MESSAGES = "getTeamMessages";
    private static final String ACTION_GET_DEFINER_MESSAGES = "getDefinerMessages";
    private static final String ACTION_GET_NEW_MESSAGES = "getNewMessages";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        ChatManager chatManager = (ChatManager) getServletContext().getAttribute("chatManager");
        if (chatManager == null) {
            chatManager = new ChatManager();
            getServletContext().setAttribute("chatManager", chatManager);
        }

        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String role = request.getParameter("role");

        if (action == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing required parameters");
            return;
        }

        try {
            switch (action) {
                case ACTION_ADD_TEAM_MESSAGE:
                    handleAddTeamMessage(request, response, chatManager, username, role);
                    break;
                case ACTION_ADD_DEFINER_MESSAGE:
                    handleAddDefinerMessage(request, response, chatManager, username);
                    break;
                case ACTION_GET_TEAM_MESSAGES:
                    handleGetTeamMessages(request, response, chatManager);
                    break;
                case ACTION_GET_DEFINER_MESSAGES:
                    handleGetDefinerMessages(request, response, chatManager);
                    break;
                case ACTION_GET_NEW_MESSAGES:
                    handleGetNewMessages(request, response, chatManager);
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Invalid action");
                    break;
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid number format");
        }
    }

    private void handleAddTeamMessage(HttpServletRequest request, HttpServletResponse response, ChatManager chatManager, String username, String role) throws IOException {
        if ("GUESSER".equals(role)) {
            String message = request.getParameter("message");
            if (message == null || message.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Message cannot be empty");
                return;
            }
            chatManager.addTeamChatMessage(message, username);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Definers are not allowed to send messages in the team chat.");
        }
    }

    private void handleAddDefinerMessage(HttpServletRequest request, HttpServletResponse response, ChatManager chatManager, String username) throws IOException {
        String message = request.getParameter("message");
        if (message == null || message.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Message cannot be empty");
            return;
        }
        chatManager.addDefinerChatMessage(message, username);
    }

    private void handleGetTeamMessages(HttpServletRequest request, HttpServletResponse response, ChatManager chatManager) throws IOException {
        int fromIndex = Integer.parseInt(request.getParameter("fromIndex"));
        List<SingleChatEntry> chatEntries = chatManager.getTeamChatMessages(fromIndex);
        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(chatEntries));
        out.flush();
    }

    private void handleGetDefinerMessages(HttpServletRequest request, HttpServletResponse response, ChatManager chatManager) throws IOException {
        int fromIndex = Integer.parseInt(request.getParameter("fromIndex"));
        List<SingleChatEntry> chatEntries = chatManager.getDefinerChatMessages(fromIndex);
        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(chatEntries));
        out.flush();
    }

    private void handleGetNewMessages(HttpServletRequest request, HttpServletResponse response, ChatManager chatManager) throws IOException {
        String chatType = request.getParameter("chatType");
        int fromIndex = Integer.parseInt(request.getParameter("fromIndex"));
        List<SingleChatEntry> chatEntries;
        if ("team".equals(chatType)) {
            chatEntries = chatManager.getTeamChatMessages(fromIndex);
        } else if ("definer".equals(chatType)) {
            chatEntries = chatManager.getDefinerChatMessages(fromIndex);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid chat type");
            return;
        }
        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(chatEntries));
        out.flush();
    }
}
