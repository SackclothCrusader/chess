package server.websocket;

import dataaccess.MySqlAuthDAO;
import dataaccess.MySqlGameDAO;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
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

    public void broadcastGame(int gameID, String excludeAuth, NotificationMessage notification) throws Exception {
        var removeList = new ArrayList<Connection>();
        var players = new ArrayList<String>();

        players.add(gameDAO.getGame(gameID).whiteUsername());
        players.add(gameDAO.getGame(gameID).blackUsername());

        for (var c : connections.values()) {
            if (c.session.isOpen() && players.contains(authDAO.getAuthData(c.cmd.getAuthToken()).username())) {
                if (!c.cmd.getAuthToken().equals(excludeAuth)) {
                    c.sendNotif(notification);
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
        GameData game = gameDAO.getGame(connection.cmd.getGameID());

        if (game == null || game.game() == null) {
            connection.sendError(new ErrorMessage("Bad gameID"));
            return;
        }

        LoadGameMessage load = new LoadGameMessage(game.game());
        connection.sendLoad(load);

        //notify
        String user = authDAO.getAuthData(auth).username();
        String msg;
        if (user.equals(game.blackUsername())) {
            msg = user + " joined the game as black.";
        } else if (user.equals(game.whiteUsername())){
            msg = user + " joined the game as white.";
        } else {
            msg = user + " joined the game as an observer.";
        }

        NotificationMessage notif = new NotificationMessage(msg);
        broadcastGame(connection.cmd.getGameID(), auth, notif);
    }

    public void badAuth(Connection connection) throws Exception{
        connection.sendError(new ErrorMessage("Bad authentication"));
    }


}