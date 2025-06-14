package server.websocket;

import chess.ChessGame;
import dataaccess.MySqlAuthDAO;
import dataaccess.MySqlGameDAO;
import exceptions.BadRequestException;
import model.GameData;
import model.Request;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import service.GameService;

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

    public void broadcastNotif(int gameID, String excludeAuth, NotificationMessage notification) throws Exception {
        var removeList = new ArrayList<Connection>();

        for (var c : connections.values()) {
            if (c.session.isOpen() && c.cmd.getGameID() == gameID) {
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

    public void broadcastLoad(int gameID, LoadGameMessage load) throws Exception {
        var removeList = new ArrayList<Connection>();

        for (var c : connections.values()) {
            if (c.session.isOpen() && c.cmd.getGameID() == gameID) {
                c.sendLoad(load);
            } else if (!c.session.isOpen()) {
                removeList.add(c);
            }
        }

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
        broadcastNotif(connection.cmd.getGameID(), auth, notif);
    }

    public void makeMove(MakeMoveCommand cmd) throws Exception {
        Connection connection = connections.get(cmd.getAuthToken());
        if (connection == null) {
            return;
        }
        if (isObserver(cmd)) {
            connection.sendError(new ErrorMessage("You are an observer"));
            return;
        }
        GameData data = gameDAO.getGame(cmd.getGameID());
        ChessGame.TeamColor colorToPlay = data.game().getTeamTurn();
        String username = authDAO.getAuthData(cmd.getAuthToken()).username();
        String activePlayer = (colorToPlay == ChessGame.TeamColor.WHITE) ? data.whiteUsername() : data.blackUsername();

        if (!username.equals(activePlayer)) {
            connection.sendError(new ErrorMessage("You cannot play now"));
            return;
        }

        GameService gameService = new GameService();
        Request.UpdateGameRequest req = new Request.UpdateGameRequest(cmd.getAuthToken(), cmd.getGameID(), cmd.getMove());

        try {
            GameData game = gameService.updateGame(req).game();
            LoadGameMessage load = new LoadGameMessage(game.game());
            broadcastLoad(cmd.getGameID(), load);
        } catch (BadRequestException e) {
            connection.sendError(new ErrorMessage("Bad move"));
            return;
        }


        NotificationMessage notif = new NotificationMessage(cmd.getMove().toString());
        broadcastNotif(cmd.getGameID(), cmd.getAuthToken(), notif);
    }

    public void resign(UserGameCommand cmd) throws Exception {
        Connection connection = connections.get(cmd.getAuthToken());
        if (connection == null) {
            return;
        }
        if (isObserver(cmd)) {
            connection.sendError(new ErrorMessage("You are an observer"));
            return;
        }

        GameData data = gameDAO.getGame(cmd.getGameID());
        String username = authDAO.getAuthData(cmd.getAuthToken()).username();
        ArrayList<String> players = new ArrayList<>();
        players.add(data.whiteUsername());
        players.add(data.blackUsername());

        if (!players.contains(username)) {
            connection.sendError(new ErrorMessage("You must be a player to resign"));
            return;
        }

        GameService gameService = new GameService();
        ChessGame.TeamColor usercolor = (username.equals(data.whiteUsername())) ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;
        Request.ResignGameRequest req = new Request.ResignGameRequest(cmd.getAuthToken(), cmd.getGameID(), usercolor);

        try {
            gameService.resign(req);
        } catch (BadRequestException e) {
            connection.sendError(new ErrorMessage("Bad move"));
            return;
        }

        NotificationMessage notif = new NotificationMessage(username + " has resigned");
        broadcastNotif(cmd.getGameID(), null, notif);
    }

    public void badAuth(Connection connection) throws Exception{
        connection.sendError(new ErrorMessage("Bad authentication"));
    }

    private boolean isObserver(UserGameCommand cmd) throws Exception{
        GameData game = gameDAO.getGame(cmd.getGameID());
        String user = authDAO.getAuthData(cmd.getAuthToken()).username();

        if (game.whiteUsername().equals(user) || game.blackUsername().equals(user)) {
            return false;
        }
        return true;
    }
}