package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import server.Request;
import server.Result;

public class GameService {
    private final MemoryGameDAO gameDAO = new MemoryGameDAO();
    private final MemoryAuthDAO authDAO = new MemoryAuthDAO();

    //list games
    public Result.ListGamesResult listGames(Request.ListGamesRequest request) {
        return new Result.ListGamesResult(gameDAO.listGames());
    }

    //create game
    public Result.CreateGameResult createGame(Request.CreateGameRequest request) throws BadRequestException, DataAccessException {
        if (request == null || request.authToken() == null || request.gameName() == null) {
            throw new BadRequestException("Error: bad request");
        }
        GameData game;
        game = gameDAO.createGame(request.gameName());
        return new Result.CreateGameResult(game.gameID());
    }

    //join game
    public Result.JoinGameResult joinGame(Request.JoinGameRequest request) throws AlreadyTakenException, BadRequestException, DataAccessException {
        if (request == null  || request.color() == null || request.authToken() == null) {
            throw new BadRequestException("Error: bad request");
        }
        GameData game = gameDAO.getGame(request.gameID());
        AuthData user = authDAO.getAuthData(request.authToken());
        if (game == null || user == null) {
            throw new BadRequestException("Error: bad request");
        }
        if (request.color() == ChessGame.TeamColor.WHITE && game.whiteUsername() != null
        || request.color() == ChessGame.TeamColor.BLACK && game.blackUsername() != null) {
            throw new AlreadyTakenException("Error: already taken");
        }
        gameDAO.addPlayer(user.username(), request.color(), request.gameID());
        return new Result.JoinGameResult();
    }
}