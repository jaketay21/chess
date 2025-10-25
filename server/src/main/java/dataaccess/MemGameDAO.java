package dataaccess;

import models.GameMap;

public class MemGameDAO implements GameDAOInterface{
    private static GameMap games = new GameMap();

    public void clearGames(){
        games.clear();
    }
}
