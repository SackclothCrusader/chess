package service;

import dataaccess.MemoryGameDAO;
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

}