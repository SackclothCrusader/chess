package server;

import chess.ChessGame;

public class Request {
    public record RegisterRequest(String username, String password, String email){}
    public record LoginRequest(String username, String password){}
    public record LogoutRequest(String authToken){}
    public record ListGamesRequest(String authToken){}
    public record CreateGameRequest(String authToken, String gameName){}
    public record JoinGameRequest(String authToken, ChessGame.TeamColor color, int gameID){}
    public record DeleteRequest(){}
}
