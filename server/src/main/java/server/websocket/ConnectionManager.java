package server.websocket;

import chess.ChessGame;
import dataaccess.MySqlAuthDAO;
import dataaccess.MySqlGameDAO;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.LoadGameMessage;
import websocket.messages.ServerMessage;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();
    private static final MySqlGameDAO gameDAO = new MySqlGameDAO();
    private static final MySqlAuthDAO authDAO = new MySqlAuthDAO();

    public void add(UserGameCommand cmd, Session session) {
        var connection = new Connection(cmd, session);
        connections.put(cmd.getAuthToken(), connection);
    }

    public void remove(String authToken) {
        connections.remove(authToken);
    }

    public void broadcastGame(int gameID, String excludeAuth, ServerMessage notification) throws Exception {
        var removeList = new ArrayList<Connection>();
        var players = new ArrayList<String>();

        players.add(gameDAO.getGame(gameID).whiteUsername());
        players.add(gameDAO.getGame(gameID).blackUsername());

        for (var c : connections.values()) {
            if (c.session.isOpen() && players.contains(authDAO.getAuthData(c.cmd.getAuthToken()).username())) {
                if (!c.cmd.getAuthToken().equals(excludeAuth)) {
                    c.send(notification);
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

    public void join(String auth) throws Exception {
        //load game
        Connection connection = connections.get(auth);
        ChessGame game = gameDAO.getGame(connection.cmd.getGameID()).game();
        LoadGameMessage load = new LoadGameMessage(game);
        connection.sendLoad(load);

        //notify
        ServerMessage notif = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        broadcastGame(connection.cmd.getGameID(), auth, notif);
    }
}