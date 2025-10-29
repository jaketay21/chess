package dataaccess;

import models.UserData;
import models.UserMap;

public class MemUserDAO implements UserDAOInterface {
    private final UserMap users = new UserMap();

    public void clearUsers() {
        users.clear();
    }

    public boolean contains(String username) {
        return users.containsKey(username);
    }

    public void addUser(UserData user) {
        users.put(user.username(), user);
    }

    public String getPassword(String username) {
        UserData user = users.get(username);
        return (user != null) ? user.password() : null;
    }
}