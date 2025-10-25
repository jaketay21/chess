package service;

import dataaccess.MemUserDAO;
import models.UserData;
import request.LoginRequest;
import request.RegisterRequest;


public class UserService {
    private final MemUserDAO userDao;

    public UserService(MemUserDAO userDao){
        this.userDao = userDao;
    }

    public boolean contains(String username){
        return userDao.contains(username);
    }

    public boolean matchesPassword(LoginRequest request){
        if(request.password() == userDao.getPassword(request.usernaame())){
            return true;
        }else{
            return false;
        }
    }

    public void addUser(RegisterRequest request){
        UserData user = new UserData(request.username(), request.password(), request.email());
        userDao.addUser(user);
    }


}
