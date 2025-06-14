package server.websocket;

import exceptions.DataAccessException;
import exceptions.UnauthorizedException;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;
import service.AuthService;
import websocket.commands.MakeMoveCommand;
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
        JsonObject json = JsonParser.parseString(message).getAsJsonObject();
        String type = json.get("commandType").getAsString();

        UserGameCommand cmd;
        switch (type) {
            case "MAKE_MOVE" -> cmd = GSON.fromJson(message, MakeMoveCommand.class);
            default -> cmd = GSON.fromJson(message, UserGameCommand.class);
        }

        String authToken = cmd.getAuthToken();
        if (!authenticate(authToken)) {
            MANAGER.badAuth(new Connection(cmd, session));
            return;
        }

        MANAGER.add(cmd, session);
        switch (cmd.getCommandType()) {
            case CONNECT -> connect(authToken);
            case MAKE_MOVE -> move((MakeMoveCommand) cmd);
            case RESIGN -> MANAGER.resign(cmd);
            case LEAVE -> MANAGER.leave(cmd);
        }
    }


    private void connect(String auth) throws Exception {
        MANAGER.join(auth);
    }

    private void move(MakeMoveCommand cmd) throws Exception{
        MANAGER.makeMove(cmd);
    }
}