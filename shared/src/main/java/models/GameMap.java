package models;

import java.util.HashMap;

public class GameMap extends HashMap<Integer, GameData> {
    public GameMap(){
        super();
    }

    public void clearGames(){
        super.clear();
    }

}
