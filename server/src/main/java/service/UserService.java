package service;

import dataaccess.MemUserDAO;
import models.Authtoken;
import models.ResponseException;
import models.UserData;
import org.eclipse.jetty.server.Response;
import request.LoginRequest;
import request.RegisterRequest;


public class UserService {
    private final MemUserDAO userDao;
    private final AuthService authService;

    public UserService(MemUserDAO userDao, AuthService authService){
        this.userDao = userDao;
        this.authService = authService;
    }

    public Authtoken registerUser(RegisterRequest request)throws ResponseException {
        if(!userDao.contains(request.username())){
            Authtoken token = authService.addAuth(request.username());
            UserData user = new UserData(request.username(), request.password(), request.email());
            userDao.addUser(user);
            return token;

        }else{
            throw new ResponseException(403);
        }


    }

    public Authtoken login(LoginRequest request)throws ResponseException {
        if(userDao.contains(request.username())){
            if(request.password().equals(userDao.getPassword(request.username()))){
                Authtoken token = authService.addAuth(request.username());
                return token;
            }else{
                throw new ResponseException(401);
            }

        }else{
            throw new ResponseException(400);
        }
    }

    public void logout(String token)throws ResponseException {
        if(!authService.isAuthorized(token)){
            throw new ResponseException(401);
        }
        authService.deleteToken(token);

    }


}
