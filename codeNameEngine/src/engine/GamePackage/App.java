package engine.GamePackage;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class App {
    private Set<Game> games;
    private Set<String> userNames;
    boolean hasAdmin;

    public App() {
        games = new HashSet<>();
        userNames = new HashSet<>();
        hasAdmin = false;
    }

    public synchronized boolean addGame(Game game) {
        return games.add(game);
    }

    public boolean isGameNameUnique(String gameName) {
        for (Game game : games) {
            if (game.getName().equalsIgnoreCase(gameName)) {
                return false;
            }
        }
        return true;
    }

    public void addUserName(String userName) {
        userNames.add(userName);
    }

    public void removeUserName(String userName) {
        userNames.remove(userName);
    }

    public Set<String> getUserNames() {
        return userNames;
    }

    public Game getGameById(int gameId) {
        for (Game game : games) {
            if (game.getGameSerialNumber() == gameId)
                return game;
        }
        return null;
    }

    public Set<Game> getGames() {
        return games;
    }

    public Set<Game> getActiveGames() {
        Set<Game> activeGames = new HashSet<>();
        for (Game game : games) {
            if (game.isActive()) {
                activeGames.add(game);
            }
        }
        return activeGames;
    }

    public Set<Game> getPendingGames() {
        Set<Game> pendingGames = new HashSet<>();
        for (Game game : games) {
            if (!game.isActive()) {
                pendingGames.add(game);
            }
        }
        return pendingGames;
    }
    public void setAdmin(boolean flag) {
        hasAdmin = flag;
    }
    public boolean isAdmin() {
        return hasAdmin;
    }

    public boolean hasActiveGame(){
        for(Game g : games){
            if(g.isActive()){
                return true;
            }
        }
        return false;
    }
    @Override
    public int hashCode() {
        return userNames.hashCode();
    }
}
