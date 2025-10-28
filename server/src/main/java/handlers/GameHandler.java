package handlers;

import io.javalin.http.Context;

import models.GameData;
import request.CreateGameRequest;
import request.JoinRequest;

import response.GameListResponse;
import service.GameService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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

    public void joinGame(Context ctx){
        String clientToken = ctx.header("authorization");

        try{
            JoinRequest request = fromJson(ctx.body(),JoinRequest.class);
            gameService.joinGame(clientToken,request);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void listGames(Context ctx){
        String clientToken = ctx.header("authorization");
        try{
           Collection<GameListResponse> gamelist = gameService.listGames(clientToken);
           ctx.status(200);
           ctx.result(toJson(gamelist));
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

}
