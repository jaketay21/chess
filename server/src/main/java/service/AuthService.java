package service;

import dataaccess.MemAuthDAO;
import models.Authtoken;
import models.ResponseException;

import java.util.UUID;

public class AuthService {
    private final MemAuthDAO authDao;

    public AuthService(MemAuthDAO authDao){
        this.authDao = authDao;
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public Authtoken addAuth(String username)throws ResponseException{
        String authToken = generateToken();
        Authtoken token = authDao.addAuth(authToken,username);
        return token;
    }

    public boolean isAuthorized(String token)throws ResponseException{
        return authDao.contains(token);
    }

    public void deleteToken(String token)throws ResponseException{
        authDao.deleteAuth(token);
    }

    public String getKey(String token)throws ResponseException {
       return authDao.getKey(token);
    }

}
