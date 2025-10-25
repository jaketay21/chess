package Dataaccess;

import Models.UserList;

public class MemUserDAO implements UserDAOInterface {
    private static UserList users = new UserList();

    public void clearUsers(){
        users.clear();
    }
}
