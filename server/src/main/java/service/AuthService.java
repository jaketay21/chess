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

    private String generateToken() throws ResponseException {
        return UUID.randomUUID().toString();
    }

    public Authtoken addAuth(String username)throws ResponseException{
        if(username == null || username.isBlank()){
            throw new ResponseException(400);
        }
        String authToken = generateToken();
        Authtoken token = authDao.addAuth(authToken,username);
        return token;
    }

    public boolean isAuthorized(String token)throws ResponseException{
        if(token == null || token.isBlank()){
            throw new ResponseException(400);
        }
        return authDao.contains(token);
    }

    public void deleteToken(String token)throws ResponseException{
        if(token == null || token.isBlank()){
            throw new ResponseException(400);
        }
        authDao.deleteAuth(token);
    }

    public String getKey(String token)throws ResponseException {
        if(token == null || token.isBlank()){
            throw new ResponseException(400);
        }
        return authDao.getKey(token);
    }

}
