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

    public Authtoken registerUser(RegisterRequest request) throws ResponseException {
        // Validate request fields (bad request)
        if (request == null ||
                request.username() == null || request.username().isBlank() ||
                request.password() == null || request.password().isBlank() ||
                request.email() == null || request.email().isBlank()) {
            throw new ResponseException(400); // Error: bad request
        }

        // Check for duplicate username (forbidden)
        if (userDao.contains(request.username())) {
            throw new ResponseException(403); // Error: already taken
        }

        // Create user and token
        UserData user = new UserData(request.username(), request.password(), request.email());
        userDao.addUser(user);
        Authtoken token = authService.addAuth(request.username());

        return token;
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
