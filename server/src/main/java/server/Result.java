package server;

import java.util.HashSet;
import java.util.List;

public class Result {
    public record RegisterResult(String username, String authToken){}
    public record LoginResult(String username, String authToken) {}
    public record LogoutResult(){}
    public record GameList(HashSet games){}
    public record CreatedGame(int gameID){}
    public record JoinedGame(){}
    public record Deleted(){}
}
