package server.websocket;

import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;

import java.io.IOException;

public class Connection {
    public UserGameCommand cmd;
    public Session session;

    public Connection(UserGameCommand cmd, Session session) {
        this.cmd = cmd;
        this.session = session;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }
}