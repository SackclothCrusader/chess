package dataaccess;

import chess.ChessGame;
import model.GameData;

import javax.xml.crypto.Data;
import java.util.Collection;

public interface GameDAO {
    //C (make game)
    abstract GameData createGame(String name) throws DataAccessException;

    //R (find game, list games)
    abstract GameData getGame(int gameID);

    abstract Collection<GameData> listGames();

    //U (add players, make move)
    abstract GameData addPlayer(String username, ChessGame.TeamColor color, int gameID) throws AlreadyTakenException, BadRequestException, DataAccessException;

    //D (delete when done?, remove player?)
}
