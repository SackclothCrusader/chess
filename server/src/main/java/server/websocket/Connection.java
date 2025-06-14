package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.*;
import java.io.IOException;

public class Connection {
    public UserGameCommand cmd;
    public Session session;
    private static final Gson GSON = new Gson();

    public Connection(UserGameCommand cmd, Session session) {
        this.cmd = cmd;
        this.session = session;
    }

    public void sendError(ErrorMessage msg) throws IOException {
        var jsonMsg = GSON.toJson(msg);
        session.getRemote().sendString(jsonMsg);
    }

    public void sendNotif(NotificationMessage msg) throws IOException {
        var jsonMsg = GSON.toJson(msg);
        session.getRemote().sendString(jsonMsg);
    }

    public void sendLoad(LoadGameMessage msg) throws IOException {
        var jsonMsg = GSON.toJson(msg);
        session.getRemote().sendString(jsonMsg);
    }
}