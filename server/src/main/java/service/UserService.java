package service;

import dataaccess.MemUserDAO;
import models.Authtoken;
import models.UserData;
import request.LoginRequest;
import request.RegisterRequest;


public class UserService {
    private final MemUserDAO userDao;
    private final AuthService authService;

    public UserService(MemUserDAO userDao, AuthService authService){
        this.userDao = userDao;
        this.authService = authService;
    }

    public Authtoken registerUser(RegisterRequest request)throws RuntimeException {
        if(!userDao.contains(request.username())){
            Authtoken token = authService.addAuth(request.username());
            UserData user = new UserData(request.username(), request.password(), request.email());
            userDao.addUser(user);
            return token;

        }else{
            throw new RuntimeException("username already in use");
        }


    }

    public Authtoken login(LoginRequest request)throws RuntimeException{
        if(userDao.contains(request.username())){
            if(request.password().equals(userDao.getPassword(request.username()))){
                Authtoken token = authService.addAuth(request.username());
                return token;
            }else{
                throw new RuntimeException("Incorrect password");
            }

        }else{
            throw new RuntimeException("No such username");
        }
    }

    public void logout(String token)throws RuntimeException{
        if(!authService.isAuthorized(token)){
            throw new RuntimeException("lol");
        }
        authService.deleteToken(token);

    }


}
