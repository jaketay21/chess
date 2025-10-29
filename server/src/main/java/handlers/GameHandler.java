package handlers;

import models.ResponseException;
import io.javalin.http.Context;

import models.GameData;
import models.ResponseException;
import request.CreateGameRequest;
import request.JoinRequest;

import response.GameListResponse;
import service.GameService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameHandler extends BaseHandler {
    private final GameService gameService;


    public GameHandler(GameService gameService){
        this.gameService = gameService;

    }

    public void createGame(Context ctx){

        try{
            CreateGameRequest request = fromJson(ctx.body(), CreateGameRequest.class);
            String clientToken = ctx.header("authorization");
           int gameID  = gameService.createGame(clientToken, request);
            ctx.result(toJson(Map.of("gameID",gameID)));
            ctx.status(200);
        } catch (ResponseException e) {
            ctx.status(e.getCode());
            ctx.result(toJson(Map.of("message",(e.getMessage()))));
        }
    }

    public void joinGame(Context ctx) {
        try {
            String clientToken = ctx.header("authorization");
            if (clientToken == null || clientToken.isBlank()) {
                throw new ResponseException(401); // Unauthorized
            }

            JoinRequest request;
            try {
                request = fromJson(ctx.body(), JoinRequest.class);
            } catch (Exception e) {
                throw new ResponseException(400); // Bad request
            }

            gameService.joinGame(clientToken, request);

            ctx.status(200);
            ctx.result(toJson("{}"));

        } catch (ResponseException e) {
            ctx.status(e.getCode());
            ctx.result(toJson(Map.of("message", e.getMessage())));
        }
    }

    public void listGames(Context ctx)throws models.ResponseException {
        try{
            String clientToken = ctx.header("authorization");
            Collection<GameListResponse> gamelist = gameService.listGames(clientToken);
            ctx.status(200);
            ctx.result(toJson(gamelist));
        }catch (models.ResponseException e){
            ctx.status(e.getCode());
            ctx.result(toJson(Map.of("message",(e.getMessage()))));

        }
    }

}
