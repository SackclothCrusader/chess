package service;

import chess.InvalidMoveException;
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
        if (request == null  || request.playerColor() == null || request.authToken() == null) {
            throw new BadRequestException("Error: bad request");
        }
        GameData game = GAME_DAO.getGame(request.gameID());
        AuthData user = AUTH_DAO.getAuthData(request.authToken());
        if (game == null || user == null) {
            throw new BadRequestException("Error: bad request");
        }
        if (request.playerColor() == ChessGame.TeamColor.WHITE && game.whiteUsername() != null
        || request.playerColor() == ChessGame.TeamColor.BLACK && game.blackUsername() != null) {
            throw new AlreadyTakenException("Error: already taken");
        }
        GAME_DAO.addPlayer(user.username(), request.playerColor(), request.gameID());
        return new Result.JoinGameResult();
    }

    public Result.JoinGameResult joinGameOverride(Request.JoinGameRequest request) throws DataAccessException {
        AuthData user = AUTH_DAO.getAuthData(request.authToken());

        GAME_DAO.addPlayer(user.username(), request.playerColor(), request.gameID());
        return new Result.JoinGameResult();
    }

    public Result.GetGameResult getGame(Request.GetGameRequest req) {
        Result.GetGameResult res = new Result.GetGameResult(GAME_DAO.getGame(req.gameID()));
        return res;
    }

    public Result.UpdateGameResult updateGame(Request.UpdateGameRequest req) throws BadRequestException, DataAccessException{
        ChessGame game;
        try {
            game = GAME_DAO.getGame(req.gameID()).game();
            game.makeMove(req.move());
        } catch (InvalidMoveException e) {
            throw new BadRequestException("Error: Bad move request");
        }

        Result.UpdateGameResult res = new Result.UpdateGameResult(GAME_DAO.updateGame(req.gameID(), game));
        return res;
    }

    public void resign(Request.ResignGameRequest req) throws BadRequestException, DataAccessException {
        ChessGame game;
        game = GAME_DAO.getGame(req.gameID()).game();
        try {
            game.resign(req.color());
        } catch (InvalidMoveException e) {
            throw new BadRequestException("Error: bad request");
        }
        GAME_DAO.updateGame(req.gameID(), game);
    }
}