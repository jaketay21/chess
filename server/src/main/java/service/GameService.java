package service;

import chess.ChessGame;
import dataaccess.MemGameDAO;
import request.CreateGameRequest;

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
}
