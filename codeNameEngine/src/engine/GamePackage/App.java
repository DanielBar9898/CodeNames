package engine.GamePackage;

import java.util.HashSet;
import java.util.Set;

public class App {
    private Set<Game> games;
    boolean hasAdmin;
    private String adminName;

    public App() {
        games = new HashSet<>();
        hasAdmin = false;
        adminName = null;
    }

    public synchronized boolean loginAdmin(String adminName) {
        if (!hasAdmin) {
            this.adminName = adminName;
            this.hasAdmin = true;
            return true;
        }
        return false;
    }
    public void removePlayer(Player player) {
        Game game=getGameById(player.getSerialGameNumber());
        if (game!=null) {
            game.removePlayer(player);
        }
    }

    public synchronized boolean logoutAdmin() {
        if (hasAdmin) {
            this.adminName = null;
            this.hasAdmin = false;
            return true;
        }
        return false;
    }

    public boolean isHasAdmin() {
        return hasAdmin;
    }

    public String getAdminName() {
        return adminName;
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
}
