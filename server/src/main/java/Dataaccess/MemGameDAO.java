package Dataaccess;

import Models.GameList;

public class MemGameDAO implements GameDAOInterface{
    private static GameList games = new GameList();

    public void clearGames(){
        games.clear();
    }
}
