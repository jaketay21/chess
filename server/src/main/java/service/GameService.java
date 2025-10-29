package service;

import dataaccess.MemGameDAO;
import models.GameData;
import models.ResponseException;
import request.CreateGameRequest;
import request.JoinRequest;
import response.GameListResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameService {
    private final MemGameDAO gameDao;
    private final AuthService authService;

    public GameService(MemGameDAO gameDao, AuthService authService) {
        this.gameDao = gameDao;
        this.authService = authService;
    }

    public int createGame(String token, CreateGameRequest request) throws ResponseException {
        // Check auth
        if (!authService.isAuthorized(token)) {
            throw new ResponseException(401);
        }

        // Validate request body
        if (request == null || request.gameName() == null || request.gameName().isBlank()) {
            throw new ResponseException(400); // bad request
        }

        // Create new game
        return gameDao.createGame(request.gameName());
    }

    public void joinGame(String token, JoinRequest request) throws ResponseException {
        // Check auth
        if (!authService.isAuthorized(token)) {
            throw new ResponseException(401);
        }

        // Validate request
        if (request == null || request.playerColor() == null || request.playerColor().isBlank()) {
            throw new ResponseException(400); // bad request
        }

        String username = authService.getKey(token);

        GameData foundGame;
        try {
            foundGame = gameDao.getGame(request.gameID());
            if (foundGame == null) {
                throw new ResponseException(400); // invalid gameID
            }

        } catch (RuntimeException e) {
            throw new ResponseException(400); // gameID invalid
        }

        switch (request.playerColor().toUpperCase()) {
            case "WHITE" -> {
                if (foundGame.getWhiteUsername() != null && !foundGame.getWhiteUsername().isBlank()) {
                    throw new ResponseException(403); // already taken
                }
                gameDao.replaceWhite(foundGame.getGameID(), username);
            }
            case "BLACK" -> {
                if (foundGame.getBlackUsername() != null && !foundGame.getBlackUsername().isBlank()) {
                    throw new ResponseException(403); // already taken
                }
                gameDao.replaceBlack(foundGame.getGameID(), username);
            }
            default -> throw new ResponseException(400); // invalid color
        }
    }

    public Collection<GameListResponse> listGames(String token) throws ResponseException {
        // Auth check
        if (!authService.isAuthorized(token)) {
            throw new ResponseException(401);
        }

        // Build response
        List<GameListResponse> gameList = new ArrayList<>();
        for (GameData x : gameDao.getGames()) {
            gameList.add(new GameListResponse(
                    x.getGameID(),
                    x.getWhiteUsername(),
                    x.getBlackUsername(),
                    x.getGameName()
            ));
        }
        return gameList;
    }
}