package engine.GamePackage;

import java.util.HashSet;
import java.util.Set;

public class AllGames {
    private Set<Game> games;

    public AllGames() {
        games = new HashSet<>();
    }

    public boolean addGame(Game game) {
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

    public Game getGameByName(String gameName) {
        for (Game game : games) {
            if (game.getName().equalsIgnoreCase(gameName)) {
                return game;
            }
        }
        return null;
    }
}
