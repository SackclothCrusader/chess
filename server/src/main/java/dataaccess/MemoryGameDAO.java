package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashSet;

public class MemoryGameDAO implements GameDAO {
    static HashSet<GameData> games = new HashSet<>();

    //C (make game)
    public GameData createGame(String name) {
        GameData game = new GameData(games.size()+1, name, null, null, new ChessGame());
        games.add(game);
        return game;
    }

    //R (find game, list games)
    public GameData getGame(int gameID){
        for (GameData i : games) {
            if (i.gameID() == gameID) {
                return i;
            }
        }
        return null;
    }

    public HashSet<GameData> listGames() {
        return games;
    }

    //U (add players, make move)
    public GameData addPlayer(String username, ChessGame.TeamColor color, int gameID) {
        GameData replace = getGame(gameID);

        GameData game;
        if (color == ChessGame.TeamColor.WHITE) {
            game = new GameData(gameID, replace.gameName(), username, replace.blackUsername(), replace.game());
        } else {
            game = new GameData(gameID, replace.gameName(), replace.whiteUsername(), username, replace.game());
        }
        games.remove(replace);
        games.add(game);
        return game;
    }
}
