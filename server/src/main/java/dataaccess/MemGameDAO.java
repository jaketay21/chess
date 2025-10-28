package dataaccess;

import chess.ChessGame;
import models.GameData;
import models.GameMap;

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
}
