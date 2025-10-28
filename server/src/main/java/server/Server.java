package server;

import dataaccess.MemAuthDAO;
import dataaccess.MemGameDAO;
import dataaccess.MemUserDAO;
import handlers.DBHandler;
import handlers.GameHandler;
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
        var gameService = new GameService(gameDAO, authService);
        var userService = new UserService(userDAO, authService);
        var dbHandler = new DBHandler(dbService);
        var userHandler = new UserHandler(userService);
        var gameHandler = new GameHandler(gameService);


        javalin.delete("/db", dbHandler::handleClearAll);
        javalin.post("/user", userHandler::Register);
        javalin.post("/session", userHandler::Login);
        javalin.delete("/session", userHandler::Logout);
        javalin.post("/game", gameHandler::createGame);
        javalin.put("/game", gameHandler::joinGame);
        javalin.get("/game", gameHandler::listGames);




    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
