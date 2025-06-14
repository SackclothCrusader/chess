package model;

import chess.ChessGame;
import chess.ChessMove;

public class Request {
    public record RegisterRequest(String username, String password, String email){}
    public record LoginRequest(String username, String password){}
    public record LogoutRequest(String authToken){}
    public record ListGamesRequest(String authToken){}
    public record CreateGameRequest(String authToken, String gameName){}
    public record JoinGameRequest(String authToken, ChessGame.TeamColor playerColor, int gameID){}
    public record GetGameRequest(String authToken, int gameID){}
    public record UpdateGameRequest(String authToken, int gameID, ChessMove move){}
    public record ResignGameRequest(String authToken, int gameID, ChessGame.TeamColor color){}
    public record DeleteRequest(){}
}