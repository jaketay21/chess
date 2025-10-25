package Dataaccess;

import Models.AuthList;

public class MemAuthDao implements AuthDAOInterface {
    private static AuthList Auths = new AuthList();

    @Override
    public void clearAuths(){
        Auths.clear();
    }
}
