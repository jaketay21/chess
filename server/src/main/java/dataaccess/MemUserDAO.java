package dataaccess;

import models.UserMap;

public class MemUserDAO implements UserDAOInterface {
    private static UserMap users = new UserMap();

    public void clearUsers(){
        users.clear();
    }
}
