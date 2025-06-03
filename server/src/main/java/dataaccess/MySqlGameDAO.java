package dataaccess;

import chess.ChessGame;
import model.GameData;
import com.google.gson.*;

import java.sql.Types;
import java.util.Collection;
import java.util.HashSet;

public class MySqlGameDAO implements GameDAO{
    private static final Gson gson = new Gson();

    public GameData createGame(String gameName) {
        try {
            var conn = DatabaseManager.getConnection();
            var statement = "INSERT INTO game (gameName, whitePlayer, blackPlayer, game) VALUES (?, ?, ?, ?)";
            try (var stmt = conn.prepareStatement(statement)) {
                stmt.setString(1, gameName);
                stmt.setNull(2, Types.VARCHAR);
                stmt.setNull(3, Types.VARCHAR);
                stmt.setString(4, gameToJson(new ChessGame()));


                if (stmt.executeUpdate() == 1) {
                    return getGame(gameName);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public GameData getGame(int gameID) {
        try {
            var conn = DatabaseManager.getConnection();
            var statement = "SELECT gameName, whitePlayer, blackPlayer, game FROM game WHERE gameID = ?";
            try (var stmt = conn.prepareStatement(statement)) {
                stmt.setInt(1, gameID);

                try (var rs = stmt.executeQuery()) {
                    rs.next();
                    var gameName = rs.getString("gameName");
                    var whiteUsername = rs.getString("whitePlayer");
                    var blackUsername = rs.getString("blackPlayer");
                    var game = gameFromJson(rs.getString("game"));

                    return new GameData(gameID, gameName, whiteUsername, blackUsername, game);
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    private GameData getGame(String gameName) {
        try {
            var conn = DatabaseManager.getConnection();
            var statement = "SELECT gameID, whitePlayer, blackPlayer, game FROM game WHERE gameName = ?";
            try (var stmt = conn.prepareStatement(statement)) {
                stmt.setString(1, gameName);

                try (var rs = stmt.executeQuery()) {
                    rs.next();
                    var gameID = rs.getInt("gameID");
                    var whiteUsername = rs.getString("whitePlayer");
                    var blackUsername = rs.getString("blackPlayer");
                    var game = gameFromJson(rs.getString("game"));

                    return new GameData(gameID, gameName, whiteUsername, blackUsername, game);
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Collection<GameData> listGames() {
        HashSet<GameData> gameList = new HashSet<>();

        try {
            var conn = DatabaseManager.getConnection();
            var statement = "SELECT gameID, gameName, whitePlayer, blackPlayer, game FROM game";
            try (var stmt = conn.prepareStatement(statement)) {

                try (var rs = stmt.executeQuery()) {
                    while(rs.next()) {
                        var gameID = rs.getInt("gameID");
                        var gameName = rs.getString("gameName");
                        var whiteUsername = rs.getString("whitePlayer");
                        var blackUsername = rs.getString("blackPlayer");
                        var game = gameFromJson(rs.getString("game"));

                        gameList.add(new GameData(gameID, gameName, whiteUsername, blackUsername, game));
                    }

                    return gameList;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    public GameData addPlayer(String username, ChessGame.TeamColor color, int gameID) {
        if (color == ChessGame.TeamColor.WHITE) {
            try {
                var conn = DatabaseManager.getConnection();
                var statement = "UPDATE game SET whitePlayer = ? WHERE gameID = ?";
                try (var stmt = conn.prepareStatement(statement)) {
                    stmt.setString(1, username);
                    stmt.setInt(2, gameID);


                    if (stmt.executeUpdate() == 1) {
                        return getGame(gameID);
                    } else {
                        return null;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                var conn = DatabaseManager.getConnection();
                var statement = "UPDATE game SET blackPlayer = ? WHERE gameID = ?";
                try (var stmt = conn.prepareStatement(statement)) {
                    stmt.setString(1, username);
                    stmt.setInt(2, gameID);


                    if (stmt.executeUpdate() == 1) {
                        return getGame(gameID);
                    } else {
                        return null;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public GameData updateGame(GameData game) {
        return null;
    }

    private String gameToJson(ChessGame game) {
        var json = gson.toJson(game);
        return json.toString();
    }

    private ChessGame gameFromJson(String jsonString) {
        ChessGame game = gson.fromJson(jsonString, ChessGame.class);
        return game;
    }
}
