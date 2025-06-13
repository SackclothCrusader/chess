package server.websocket;

import dataaccess.MySqlAuthDAO;
import dataaccess.MySqlGameDAO;
import exceptions.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(UserGameCommand cmd, Session session) {
        var connection = new Connection(cmd, session);
        connections.put(cmd.getAuthToken(), connection);
    }

    public void remove(String authToken) {
        connections.remove(authToken);
    }

    public void broadcastGame(int gameID, String excludeAuth, ServerMessage notification) throws IOException, DataAccessException {
        MySqlGameDAO gameDAO = new MySqlGameDAO();
        MySqlAuthDAO authDAO = new MySqlAuthDAO();

        var removeList = new ArrayList<Connection>();
        var players = new ArrayList<String>();

        players.add(gameDAO.getGame(gameID).whiteUsername());
        players.add(gameDAO.getGame(gameID).blackUsername());

        for (var c : connections.values()) {
            if (c.session.isOpen() && players.contains(authDAO.getAuthData(c.cmd.getAuthToken()).username())) {
                if (!c.cmd.getAuthToken().equals(excludeAuth)) {
                    c.send(notification.toString());
                }
            } else if (!c.session.isOpen()) {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.cmd.getAuthToken());
        }
    }
}