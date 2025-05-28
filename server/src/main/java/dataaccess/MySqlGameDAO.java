package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public class MySqlGameDAO implements GameDAO{
    public GameData createGame(String gameID) {
        return null;
    }

    public GameData getGame(int gameID) {
        return null;
    }

    public Collection<GameData> listGames() {
        return null;
    }

    public GameData addPlayer(String username, ChessGame.TeamColor color, int gameID) {
        return null;
    }
}
