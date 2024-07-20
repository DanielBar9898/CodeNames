package engine.GamePackage;

import java.util.HashSet;
import java.util.Set;

public class AllGames {
    private Set<Game> games;

    public AllGames() {
        games = new HashSet<>();
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
            if(game.getGameNumber()==gameId)
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
            if(game.isActive()){
                activeGames.add(game);
            }
        }
        return activeGames;
    }
}
