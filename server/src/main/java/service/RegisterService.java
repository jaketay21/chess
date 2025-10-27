package service;


import dataaccess.MemUserDAO;
import models.Authtoken;
import models.UserData;
import request.RegisterRequest;

public class RegisterService {
    private final MemUserDAO userDao;
    private final AuthService authService;

    public RegisterService(MemUserDAO userDao, AuthService authService){
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

}
