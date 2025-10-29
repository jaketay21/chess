package service;

import dataaccess.MemAuthDAO;
import models.Authtoken;
import models.ResponseException;
import request.RegisterRequest;

import java.util.UUID;

public class AuthService {
    private final MemAuthDAO AuthDao;

    public AuthService(MemAuthDAO AuthDao){
        this.AuthDao = AuthDao;
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public Authtoken addAuth(String username)throws ResponseException{
        String authToken = generateToken();
        Authtoken token = AuthDao.addAuth(username,authToken);
        return token;
    }

    public boolean isAuthorized(String token)throws ResponseException{
        return AuthDao.contains(token);
    }

    public void deleteToken(String token)throws ResponseException{
        AuthDao.deleteAuth(token);
    }

    public String getKey(String token)throws ResponseException {
       return AuthDao.getKey(token);
    }

}
