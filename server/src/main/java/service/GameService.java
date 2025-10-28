package service;

import chess.ChessGame;
import dataaccess.MemGameDAO;
import models.GameData;
import request.CreateGameRequest;
import request.JoinRequest;
import response.GameListResponse;

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

    public int createGame(String token, CreateGameRequest request)throws RuntimeException{
        if(!authService.isAuthorized(token)){
            throw new RuntimeException("lol");
        }

        int gameID = gameDao.createGame(request.gameName());
        return gameID;

    }

    public void joinGame(String token, JoinRequest request)throws RuntimeException{
        if(!authService.isAuthorized(token)){
            throw new RuntimeException("lol");
        }
        String username = authService.getKey(token);
        GameData foundGame = gameDao.getGame(request.gameID());

        switch(request.playerColor()){
            case "WHITE":
                //check and for opening in White
                if(!foundGame.getWhiteUsername().isBlank()){
                    throw new RuntimeException("spot filled");
                }
                gameDao.replaceWhite(foundGame.getGameID(),username);

                break;
            case "BLACK":
                //add black;
                if(!foundGame.getBlackUsername().isBlank()){
                    throw new RuntimeException("already filled");
                }
                gameDao.replaceBlack(foundGame.getGameID(), username);
                break;
            default:
                throw new RuntimeException("bac input");

        }
    }

    public  Collection<GameListResponse> listGames(String token)throws RuntimeException{
        if(!authService.isAuthorized(token)){
            throw new RuntimeException("lol");
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
