package dataaccess;

import models.AuthMap;

public class MemAuthDAO implements AuthDAOInterface {
    private static AuthMap Auths = new AuthMap();

    public void clearAuths(){
        Auths.clear();
    }
}
