package server;

import java.util.HashSet;

public class Result {
    public record RegisterResult(String username, String authToken){}
    public record LoginResult(String username, String authToken) {}
    public record LogoutResult(){}
    public record ListGamesResult(HashSet games){}
    public record CreatedGameResult(int gameID){}
    public record JoinedGameResult(){}
    public record DeleteResult(){}
}
