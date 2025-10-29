package handlers;

import io.javalin.http.Context;
import models.ResponseException;
import request.CreateGameRequest;
import request.JoinRequest;
import response.GameListResponse;
import service.GameService;

import java.util.Collection;
import java.util.Map;

public class GameHandler extends BaseHandler {
    private final GameService gameService;

    public GameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public void createGame(Context ctx) {
        try {
            String clientToken = ctx.header("authorization");
            if (clientToken == null || clientToken.isBlank()) {
                throw new ResponseException(401); // Unauthorized
            }

            CreateGameRequest request;
            try {
                request = fromJson(ctx.body(), CreateGameRequest.class);
            } catch (Exception e) {
                throw new ResponseException(400); // Bad request
            }

            int gameID = gameService.createGame(clientToken, request);
            ctx.status(200);
            ctx.result(toJson(Map.of("gameID", gameID)));

        } catch (ResponseException e) {
            ctx.status(e.getCode());
            ctx.result(toJson(Map.of("message", e.getMessage())));
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
            ctx.result(toJson(Map.of()));


        } catch (ResponseException e) {
            ctx.status(e.getCode());
            ctx.result(toJson(Map.of("message", e.getMessage())));
        }
    }

    public void listGames(Context ctx) {
        try {
            String clientToken = ctx.header("authorization");
            if (clientToken == null || clientToken.isBlank()) {
                throw new ResponseException(401); // Unauthorized
            }

            Collection<GameListResponse> gamelist = gameService.listGames(clientToken);
            ctx.status(200);
            ctx.result(toJson(gamelist));

        } catch (ResponseException e) {
            ctx.status(e.getCode());
            ctx.result(toJson(Map.of("message", e.getMessage())));
        }
    }
}