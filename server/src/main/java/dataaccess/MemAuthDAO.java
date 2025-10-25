package dataaccess;

import models.AuthMap;
import models.Authtoken;

public class MemAuthDAO implements AuthDAOInterface {
    private static AuthMap Auths = new AuthMap();

    public Authtoken addAuths(String username, String token){
        Auths.put(username,new Authtoken(username,token));
        return Auths.get(username);
    }
    public void clearAuths(){
        Auths.clear();
    }
}
