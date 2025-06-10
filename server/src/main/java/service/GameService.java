package service;

import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.DataAccessException;
import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.Request;
import model.Result;

public class GameService {
    private static final MySqlGameDAO GAME_DAO = new MySqlGameDAO();
    private static final MySqlAuthDAO AUTH_DAO = new MySqlAuthDAO();

    //list games
    public Result.ListGamesResult listGames(Request.ListGamesRequest request) {
        return new Result.ListGamesResult(GAME_DAO.listGames());
    }

    //create game
    public Result.CreateGameResult createGame(Request.CreateGameRequest request) throws BadRequestException, DataAccessException {
        if (request == null || request.authToken() == null || request.gameName() == null) {
            throw new BadRequestException("Error: bad request");
        }
        GameData game;
        game = GAME_DAO.createGame(request.gameName());
        return new Result.CreateGameResult(game.gameID());
    }

    //join game
    public Result.JoinGameResult joinGame(Request.JoinGameRequest request) throws AlreadyTakenException, BadRequestException, DataAccessException {
        if (request == null  || request.color() == null || request.authToken() == null) {
            throw new BadRequestException("Error: bad request");
        }
        GameData game = GAME_DAO.getGame(request.gameID());
        AuthData user = AUTH_DAO.getAuthData(request.authToken());
        if (game == null || user == null) {
            throw new BadRequestException("Error: bad request");
        }
        if (request.color() == ChessGame.TeamColor.WHITE && game.whiteUsername() != null
        || request.color() == ChessGame.TeamColor.BLACK && game.blackUsername() != null) {
            throw new AlreadyTakenException("Error: already taken");
        }
        GAME_DAO.addPlayer(user.username(), request.color(), request.gameID());
        return new Result.JoinGameResult();
    }

    public Result.GetGameResult getGame(Request.GetGameRequest req) {
        Result.GetGameResult res = new Result.GetGameResult(GAME_DAO.getGame(req.gameID()));
        System.out.println(res);
        return res;
    }
}