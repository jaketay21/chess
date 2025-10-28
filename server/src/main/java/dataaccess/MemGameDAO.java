package dataaccess;

import chess.ChessGame;
import models.GameData;
import models.GameMap;

import java.util.*;

public class MemGameDAO implements GameDAOInterface{
    private static GameMap games = new GameMap();
    private static int gameNum = 1000;

    public void clearGames(){
        games.clear();
        gameNum = 1000;
    }


    public int createGame(String gameName){
        ChessGame game = new ChessGame();
        int gameID = gameNum;
        GameData newGame = new GameData(gameID,gameName,game);
        games.put(gameID, newGame);
        gameNum++;
        return gameID;

    }

    public GameData getGame(int gameID)throws RuntimeException{
        if(!games.containsKey(gameID)){
            throw new RuntimeException("no game here by that name");
        }
        return games.get(gameID);
    }

    public void replaceWhite(int gameID, String username){
        GameData found = games.get(gameID);
        found.setWhiteUsername(username);
    }

    public void replaceBlack(int gameID, String username){
        GameData found = games.get(gameID);
        found.setBlackUsername(username);
    }

    public Collection<GameData> getGames(){
        List<GameData> gameList = new ArrayList<>(games.values());
        return gameList;

    }

}
