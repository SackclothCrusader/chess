package service;

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
    public Result.CreateGameResult createGame(Request.CreateGameRequest request) {
        GameData game = new MemoryGameDAO().createGame(request.gameName());
        return new Result.CreateGameResult(game.gameID());
    }

    //join game
    public Result.JoinGameResult joinGame(Request.JoinGameRequest request) throws AlreadyTakenException, BadRequestException {
        AuthData user = new MemoryAuthDAO().getAuthData(request.authToken());
        try {
            new MemoryGameDAO().addPlayer(user.username(), request.color(), request.gameID());
        } catch (AlreadyTakenException e) {
            throw e;
        } catch (BadRequestException e) {
            throw e;
        }
        return new Result.JoinGameResult();
    }
}