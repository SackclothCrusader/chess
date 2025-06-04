package dataaccess;

import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.DataAccessException;
import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public interface GameDAO {
    //C (make game)
    abstract GameData createGame(String name) throws DataAccessException;

    //R (find game, list games)
    abstract GameData getGame(int gameID);

    abstract Collection<GameData> listGames();

    //U (add players, make move)
    abstract GameData addPlayer(String username, ChessGame.TeamColor color, int gameID)
            throws AlreadyTakenException, BadRequestException, DataAccessException;

    //D (delete when done?, remove player?)
}
