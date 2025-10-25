package server;

import dataaccess.MemAuthDAO;
import dataaccess.MemGameDAO;
import dataaccess.MemUserDAO;
import handlers.DBHandler;
import io.javalin.*;
import service.DBService;


public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        var userDAO = new MemUserDAO();
        var gameDAO = new MemGameDAO();
        var authDAO = new MemAuthDAO();

        var dbService = new DBService(userDAO, gameDAO, authDAO);
        var dbHandler = new DBHandler(dbService);

        javalin.delete("/db", dbHandler::handleClearAll);




        // Register your endpoints and exception handlers here.

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
