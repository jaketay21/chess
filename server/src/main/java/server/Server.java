package server;

import dataaccess.MemAuthDAO;
import dataaccess.MemGameDAO;
import dataaccess.MemUserDAO;
import handlers.DBHandler;
import handlers.UserHandler;
import io.javalin.*;
import service.AuthService;
import service.DBService;
import service.RegisterService;
import service.UserService;


public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        var userDAO = new MemUserDAO();
        var gameDAO = new MemGameDAO();
        var authDAO = new MemAuthDAO();

        var dbService = new DBService(userDAO, gameDAO, authDAO);
        var userService = new UserService(userDAO);
        var authService = new AuthService(authDAO);
        var registerService = new RegisterService(userDAO, authService);
        var dbHandler = new DBHandler(dbService);
        var userHandler = new UserHandler(userService, authService, registerService);


        javalin.delete("/db", dbHandler::handleClearAll);
        javalin.post("/user", userHandler::Register);
        javalin.post("/session", userHandler::Login);



    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
