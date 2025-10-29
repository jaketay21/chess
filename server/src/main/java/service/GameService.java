package service;

import chess.ChessGame;
import dataaccess.MemGameDAO;
import models.GameData;
import models.ResponseException;
import passoff.exception.ResponseParseException;
import request.CreateGameRequest;
import request.JoinRequest;
import response.GameListResponse;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class GameService {
    private final MemGameDAO gameDao;
    private final AuthService authService;

    public GameService(MemGameDAO gameDao, AuthService authService){
        this.gameDao = gameDao;
        this.authService = authService;
    }

    public int createGame(String token, CreateGameRequest request)throws ResponseException{
        if(!authService.isAuthorized(token)){
            throw new ResponseException(401);
        }

        int gameID = gameDao.createGame(request.gameName());
        return gameID;

    }

    public void joinGame(String token, JoinRequest request)throws ResponseException{
        if(!authService.isAuthorized(token)){
            throw new ResponseException(401);
        }
        String username = authService.getKey(token);
        GameData foundGame = gameDao.getGame(request.gameID());

        switch(request.playerColor()){
            case "WHITE":
                //check and for opening in White
                if(!foundGame.getWhiteUsername().isBlank()){
                    throw new ResponseException(403);
                }
                gameDao.replaceWhite(foundGame.getGameID(),username);

                break;
            case "BLACK":
                //add black;
                if(!foundGame.getBlackUsername().isBlank()){
                    throw new ResponseException(403);
                }
                gameDao.replaceBlack(foundGame.getGameID(), username);
                break;
            default:
                throw new ResponseException(400);

        }
    }

    public  Collection<GameListResponse> listGames(String token)throws ResponseException {
        if(!authService.isAuthorized(token)){
            throw new ResponseException(401);
        }
        List<GameListResponse> gameList = new ArrayList<>();
        Collection<GameData> games = gameDao.getGames();
        for(GameData x: games){
            GameListResponse game = new GameListResponse(x.getGameID(),x.getWhiteUsername(),x.getBlackUsername(),x.getGameName());
            gameList.add(game);
        }
        return gameList;

    }
}
