package model;

import java.util.HashSet;

public class Result {
    public record RegisterResult(String username, String authToken){}
    public record LoginResult(String username, String authToken) {}
    public record LogoutResult(){}
    public record ListGamesResult(HashSet<GameData> games){}
    public record CreateGameResult(int gameID){}
    public record JoinGameResult(){}
    public record GetGameResult(GameData game){}
    public record UpdateGameResult(){}
    public record DeleteResult(){}
}
