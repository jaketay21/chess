package dataaccess;

import models.AuthMap;
import models.Authtoken;

public class MemAuthDAO implements AuthDAOInterface {
    private static AuthMap Auths = new AuthMap();

    public Authtoken addAuths(String username, String token){
        Auths.put(username,token);
        Authtoken authToken = new Authtoken(username,token);
        return authToken;
    }
    public void clearAuths(){
        Auths.clear();
    }

    public boolean contains(String token){
        return Auths.containsValue(token);
    }
}
