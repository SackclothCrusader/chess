package model;

import java.util.HashSet;

public class Result {
    public record RegisterResult(String username, String authToken){}
    public record LoginResult(String username, String authToken) {}
    public record LogoutResult(){}
    public record ListGamesResult(HashSet games){}
    public record CreateGameResult(int gameID){}
    public record JoinGameResult(){}
    public record DeleteResult(){}
}
