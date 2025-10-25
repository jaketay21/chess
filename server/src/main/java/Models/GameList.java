package Models;

import java.util.HashMap;

public class GameList extends HashMap<Integer, Game> {
    public GameList(){
        super();
    }

    public void clearGames(){
        super.clear();
    }
}
