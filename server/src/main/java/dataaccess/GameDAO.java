package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashSet;

public interface GameDAO {
    HashSet<GameData> games = new HashSet<>();

    //C (make game)
    default GameData createGame(String name) {
        GameData game = new GameData(games.size()+1, name, "", "", new ChessGame());
        games.add(game);
        return game;
    }

    //R (find game, list games)
    default GameData getGame(int gameID){
        for (GameData i : games) {
            if (i.gameID() == gameID) {
                return i;
            }
        }
        return null;
    }

    default Collection<GameData> listGames() {
        return games;
    }

    //U (add players, make move)
    default GameData addPlayer(String username, ChessGame.TeamColor color, int gameID) throws DataAccessException{
        GameData replace = getGame(gameID);

        if (replace == null) {
            throw new DataAccessException("Error: bad request");
        }
        if ((color == ChessGame.TeamColor.BLACK && !replace.blackUsername().isBlank()) ||
                (color == ChessGame.TeamColor.WHITE && !replace.whiteUsername().isBlank())) {
            throw new DataAccessException("Error: color already taken");
        }

        GameData game;
        if (color == ChessGame.TeamColor.WHITE) {
            game = new GameData(gameID, replace.gameName(), username, replace.blackUsername(), replace.game());
        }
        else {
            game = new GameData(gameID, replace.gameName(), replace.whiteUsername(), username, replace.game());
        }
        games.remove(replace);
        games.add(game);
        return game;
    }

//    default GameData makeMove(GameData game, ChessGame board, chess.ChessMove move) {
//        game = new GameData(game.gameID(), game.gameName(), game.whiteUsername(), game.blackUsername(), board.makeMove());
//    }

    //D (delete when done?, remove player?)
}
