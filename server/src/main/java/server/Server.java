package server;

import dataaccess.MemAuthDAO;
import dataaccess.MemGameDAO;
import dataaccess.MemUserDAO;
import handlers.DBHandler;
import handlers.UserHandler;
import io.javalin.*;
import org.eclipse.jetty.util.log.Log;
import service.*;


public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        var userDAO = new MemUserDAO();
        var gameDAO = new MemGameDAO();
        var authDAO = new MemAuthDAO();

        var dbService = new DBService(userDAO, gameDAO, authDAO);
        var authService = new AuthService(authDAO);
        var userService = new UserService(userDAO, authService);
        var dbHandler = new DBHandler(dbService);
        var userHandler = new UserHandler(userService, authService);


        javalin.delete("/db", dbHandler::handleClearAll);
        javalin.post("/user", userHandler::Register);
        javalin.post("/session", userHandler::Login);
        javalin.delete("/session", userHandler::Logout);
        //javalin.get("/games", gameHandler::List);



    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
