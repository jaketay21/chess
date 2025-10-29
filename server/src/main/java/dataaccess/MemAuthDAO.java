package dataaccess;

import models.AuthMap;
import models.Authtoken;

public class MemAuthDAO implements AuthDAOInterface {
    private final AuthMap auths = new AuthMap();

    public Authtoken addAuth(String username, String token) {
        auths.put(token, username);
        return new Authtoken(username, token);
    }

    public void clearAuths() {
        auths.clear();
    }

    public void deleteAuth(String token) {
        auths.remove(token);
    }

    public boolean contains(String token) {
        return auths.containsKey(token);
    }

    public String getKey(String token) {
        return auths.get(token);
    }
}