package service;

import dataaccess.MemUserDAO;

public class UserService {
    private final MemUserDAO userDao;

    public UserService(MemUserDAO userDao){
        this.userDao = userDao;
    }

    public boolean checkUsername(String username){
        return userDao.contains(username);
    }
}
