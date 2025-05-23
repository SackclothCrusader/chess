package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import server.Request;
import server.Result;

public class GameService {
    //list games
    public Result.ListGamesResult listGames(Request.ListGamesRequest request) {
        return new Result.ListGamesResult(new MemoryGameDAO().listGames());
    }

    //create game
    public Result.CreateGameResult createGame(Request.CreateGameRequest request) throws BadRequestException {
        if (request == null || request.authToken() == null || request.gameName() == null) {
            throw new BadRequestException("Error: bad request");
        }
        GameData game = new MemoryGameDAO().createGame(request.gameName());
        return new Result.CreateGameResult(game.gameID());
    }

    //join game
    public Result.JoinGameResult joinGame(Request.JoinGameRequest request) throws AlreadyTakenException, BadRequestException {
        if (request == null  || request.color() == null || request.authToken() == null) {
            throw new BadRequestException("Error: bad request");
        }
        GameData game = new MemoryGameDAO().getGame(request.gameID());
        AuthData user = new MemoryAuthDAO().getAuthData(request.authToken());
        if (game == null || user == null) {
            throw new BadRequestException("Error: bad request");
        }
        if (request.color() == ChessGame.TeamColor.WHITE && game.whiteUsername() != null
        || request.color() == ChessGame.TeamColor.BLACK && game.blackUsername() != null) {
            throw new AlreadyTakenException("Error: already taken");
        }

        new MemoryGameDAO().addPlayer(user.username(), request.color(), request.gameID());
        return new Result.JoinGameResult();
    }
}