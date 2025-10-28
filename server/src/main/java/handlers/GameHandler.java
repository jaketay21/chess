package handlers;

import io.javalin.http.Context;
import models.Authtoken;
import request.CreateGameRequest;
import service.GameService;

public class GameHandler extends BaseHandler {
    private final GameService gameService;

    public GameHandler(GameService gameService){
        this.gameService = gameService;
    }

    public void createGame(Context ctx){
        CreateGameRequest request = fromJson(ctx.body(), CreateGameRequest.class);
        String clientToken = ctx.header("authorization");
        try{
           int gameID  = gameService.createGame(clientToken, request);
            ctx.result(toJson(gameID));
            ctx.status(200);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
