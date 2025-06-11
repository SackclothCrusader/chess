package model;

import chess.ChessGame;

public record GameData(int gameID, String gameName, String whiteUsername, String blackUsername, ChessGame game) {
    @Override
    public String toString() {
        return "GameID: " + gameID + ", Name: " + gameName + ", White: " + whiteUsername + ", Black: " + blackUsername;
    }
}