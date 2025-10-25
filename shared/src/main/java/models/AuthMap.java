package models;

import java.util.HashMap;

public class AuthMap extends HashMap<String, Authtoken> {



    public AuthMap(){
        super();
    }

    public void clearAuths(){
        super.clear();
    }
}
