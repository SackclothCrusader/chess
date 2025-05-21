package server;

public class Request {
    public record RegisterRequest(String username, String password, String email) {}
    public record LoginRequest(String username, String password){}
    public record LogoutRequest(String authToken){}
    public record ListGames(String authToken){}
    public record CreateGame(String authToken, String gameName){}
}
