package engine.chatPackage;

import java.util.ArrayList;
import java.util.List;

/*
This class is thread safe in the manner of adding\fetching new chat lines, but not in the manner of getting the size of the list
if the use of getVersion is to be incorporated with other methods here - it should be synchronized from the user code
 */
public class ChatManager {

    private final List<SingleChatEntry> teamChatMessages;
    private final List<SingleChatEntry> definerChatMessages;

    public ChatManager() {
        teamChatMessages = new ArrayList<>();
        definerChatMessages = new ArrayList<>();
    }

    public synchronized void addTeamChatMessage(String message, String username) {
        teamChatMessages.add(new SingleChatEntry(message, username));
    }

    public synchronized void addDefinerChatMessage(String message, String username) {
        definerChatMessages.add(new SingleChatEntry(message, username));
    }

    public synchronized List<SingleChatEntry> getTeamChatMessages(int fromIndex) {
        if (fromIndex < 0 || fromIndex > teamChatMessages.size()) {
            fromIndex = 0;
        }
        return teamChatMessages.subList(fromIndex, teamChatMessages.size());
    }

    public synchronized List<SingleChatEntry> getDefinerChatMessages(int fromIndex) {
        if (fromIndex < 0 || fromIndex > definerChatMessages.size()) {
            fromIndex = 0;
        }
        return definerChatMessages.subList(fromIndex, definerChatMessages.size());
    }

    public int getTeamChatVersion() {
        return teamChatMessages.size();
    }

    public int getDefinerChatVersion() {
        return definerChatMessages.size();
    }
}



