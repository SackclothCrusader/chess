package server.websocket;

import exceptions.DataAccessException;
import exceptions.UnauthorizedException;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;
import service.AuthService;
import websocket.commands.UserGameCommand;
import com.google.gson.*;

@WebSocket
public class WSHandler {
    private static final Gson GSON = new Gson();
    private static final ConnectionManager MANAGER = new ConnectionManager();

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

        MANAGER.add(cmd, session);
        switch (cmd.getCommandType()) {
            case CONNECT -> connect(authToken);
            case LEAVE -> leave(cmd);
        }
    }

    private void connect(String auth) throws Exception {
        MANAGER.join(auth);
    }

    private void leave(UserGameCommand cmd) throws Exception {
        MANAGER.remove(cmd.getAuthToken());
    }
}