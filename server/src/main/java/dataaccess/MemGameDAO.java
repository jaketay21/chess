package dataaccess;

import chess.ChessGame;
import models.GameData;
import models.GameMap;

import java.util.*;

public class MemGameDAO implements GameDAOInterface {
    private final GameMap games = new GameMap();
    private int gameNum = 1;

    public void clearGames() {
        games.clear();
        gameNum = 1;
    }

    public int createGame(String gameName) {
        ChessGame game = new ChessGame();
        int gameID = gameNum++;
        GameData newGame = new GameData(gameID, gameName, game);
        games.put(gameID, newGame);
        return gameID;
    }

    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    public void replaceWhite(int gameID, String username) {
        GameData found = games.get(gameID);
        if (found != null) {
            found.setWhiteUsername(username);
        }
    }

    public void replaceBlack(int gameID, String username) {
        GameData found = games.get(gameID);
        if (found != null) {
            found.setBlackUsername(username);
        }
    }

    public Collection<GameData> getGames() {
        return new ArrayList<>(games.values());
    }
}