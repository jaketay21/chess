package dataaccess;

import models.AuthMap;
import models.Authtoken;

import java.util.Map;
import java.util.Objects;

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
    public void deleteAuth(String token){

        Auths.entrySet().removeIf(entry -> Objects.equals(entry.getValue(), token));

    }
    public boolean contains(String token){
        return Auths.containsValue(token);
    }

    public String getKey(String token){
        for (Map.Entry<String, String> entry : Auths.entrySet()) {
            if (Objects.equals(entry.getValue(), token)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
