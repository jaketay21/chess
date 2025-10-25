package models;

import java.util.HashMap;

public class UserMap extends HashMap<String, UserData> {
    public UserMap(){
        super();
    }

    public void clearUsers(){
        super.clear();
    }
}
