package service;

import dataaccess.MemAuthDAO;
import models.Authtoken;
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

    public Authtoken addAuth(RegisterRequest request){
        String authToken = generateToken();
        Authtoken token = AuthDao.addAuths(request.username(),authToken);
        return token;
    }

}
