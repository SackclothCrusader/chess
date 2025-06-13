package server.websocket;

import exceptions.DataAccessException;
import exceptions.UnauthorizedException;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.AuthService;
import spark.Session;
import websocket.commands.UserGameCommand;
import com.google.gson.*;

import java.util.concurrent.ConcurrentHashMap;

@WebSocket
public class WSHandler {
    private static final Gson GSON = new Gson();
    private static ConcurrentHashMap<Integer, Session> connections = new ConcurrentHashMap<>();

    //authorization
    private static boolean authenticate(String authToken) throws DataAccessException {
        return AuthService.authenticate(authToken);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {

        UserGameCommand cmd = GSON.fromJson(message, UserGameCommand.class);
        String authToken = cmd.getAuthToken();

        if (!authenticate(authToken)) {
            throw new UnauthorizedException("Error: unauthorized");
        }
    }

    private void saveSession(UserGameCommand cmd, Session session) {
        if (!ConcurrentHashMap.newKeySet().getMap().containsKey(cmd)) {

        }
    }
}