package models;

import chess.ChessGame;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {

    public GameData(int gameID, String gameName, ChessGame game) {
        this(gameID, "", "", gameName, game);
    }
}
