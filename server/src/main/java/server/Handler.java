package server;

import exceptions.*;
import chess.ChessGame;
import com.google.gson.*;
import model.Request;
import model.Result;
import server.websocket.ConnectionManager;
import service.AuthService;
import service.ClearService;
import service.GameService;
import service.UserService;
import java.lang.reflect.Type;

public class Handler {
    static final Gson GSON = new GsonBuilder().registerTypeHierarchyAdapter(Exception.class, new ExceptionTypeAdapter()).create();

    //authorization
    private static boolean authenticate(String authToken) throws DataAccessException {
        return AuthService.authenticate(authToken);
    }

    public static class UserHandler extends Handler {
        //register [POST] /user
        public static Object register(spark.Request req, spark.Response res) {
            Request.RegisterRequest registerRequest = new Gson().fromJson(req.body(), Request.RegisterRequest.class);
            Result.RegisterResult registerResult;
            try {
                registerResult = new UserService().register(registerRequest);
            } catch (AlreadyTakenException e) {
                res.status(403);
                return GSON.toJson(e);
            } catch (BadRequestException e) {
                res.status(400);
                return GSON.toJson(e);
            } catch (DataAccessException e) {
                res.status(500);
                return GSON.toJson(e);
            }
            return new Gson().toJson(registerResult);
        }

        //login [POST] /session
        public static Object login(spark.Request req, spark.Response res) {
            Request.LoginRequest loginRequest = new Gson().fromJson(req.body(), Request.LoginRequest.class);
            Result.LoginResult loginResult;
            try {
                loginResult = new UserService().login(loginRequest);
            } catch (UnauthorizedException e) {
                res.status(401);
                return GSON.toJson(e);
            } catch (BadRequestException e) {
                res.status(400);
                return GSON.toJson(e);
            } catch (DataAccessException e) {
                res.status(500);
                return GSON.toJson(e);
            }

            return new Gson().toJson(loginResult);
        }

        //logout [DELETE] /session
        public static Object logout(spark.Request req, spark.Response res) {
            String authToken = req.headers("authorization");
            try {
                if (!authenticate(authToken)) {
                    res.status(401);
                    return GSON.toJson(new UnauthorizedException("Error: unauthorized"));
                }
            } catch (DataAccessException e) {
                res.status(500);
                return GSON.toJson(e);
            }
            Request.LogoutRequest logoutRequest = new Request.LogoutRequest(authToken);
            Result.LogoutResult logoutResult;
            try {
               logoutResult = new UserService().logout(logoutRequest);
            } catch (DataAccessException e) {
                res.status(500);
                return GSON.toJson(e);
            }

            return new Gson().toJson(logoutResult);
        }
    }

    public static class GameHandler extends Handler {
        //list games [GET] /game
        public static Object listGames(spark.Request req, spark.Response res) {
            String authToken = req.headers("authorization");
            try {
                if (!authenticate(authToken)) {
                    res.status(401);
                    return GSON.toJson(new UnauthorizedException("Error: unauthorized"));
                }
            } catch (DataAccessException e) {
                res.status(500);
                return GSON.toJson(e);
            }

            Request.ListGamesRequest listGamesRequest = new Request.ListGamesRequest(authToken);
            Result.ListGamesResult listGamesResult = new GameService().listGames(listGamesRequest);

            return new Gson().toJson(listGamesResult);
        }

        //create game [POST] /game
        public static Object createGame(spark.Request req, spark.Response res) {
            String authToken = req.headers("authorization");
            try {
                if (!authenticate(authToken)) {
                    res.status(401);
                    return GSON.toJson(new UnauthorizedException("Error: unauthorized"));
                }
            } catch (DataAccessException e) {
                res.status(500);
                return GSON.toJson(e);
            }

            var name = JsonParser.parseString(req.body()).getAsJsonObject().get("gameName");
            if (name == null) {
                res.status(400);
                return GSON.toJson(new BadRequestException("Error: bad request"));
            }

            Request.CreateGameRequest createGameRequest = new Request.CreateGameRequest(authToken, name.getAsString());

            Result.CreateGameResult createGameResult;
            try {
                createGameResult  = new GameService().createGame(createGameRequest);
            } catch (BadRequestException e) {
                res.status(400);
                return GSON.toJson(e);
            } catch (DataAccessException e) {
                res.status(500);
                return GSON.toJson(e);
            }

            return new Gson().toJson(createGameResult);
        }

        //join game [PUT] /game
        public static Object joinGame(spark.Request req, spark.Response res) {
            String authToken = req.headers("authorization");
            try {
                if (!authenticate(authToken)) {
                    res.status(401);
                    return GSON.toJson(new UnauthorizedException("Error: unauthorized"));
                }
            } catch (DataAccessException e) {
                res.status(500);
                return GSON.toJson(e);
            }

            var tmp = JsonParser.parseString(req.body()).getAsJsonObject();
            if (tmp.get("playerColor") == null) {
                res.status(400);
                return GSON.toJson(new BadRequestException("Error: bad request"));
            }
            String colorCheck = tmp.get("playerColor").getAsString();
            if (!(colorCheck.equals("WHITE") || colorCheck.equals("BLACK"))) {
                res.status(400);
                return GSON.toJson(new BadRequestException("Error: bad request"));
            }

            ChessGame.TeamColor color = ChessGame.TeamColor.valueOf(colorCheck);
            var gameID = tmp.get("gameID");
            if (gameID == null) {
                res.status(400);
                return GSON.toJson(new BadRequestException("Error: bad request"));
            }

            Request.JoinGameRequest joinGameRequest = new Request.JoinGameRequest(authToken, color, gameID.getAsInt());
            Result.JoinGameResult joinGameResult;
            try {
                joinGameResult = new GameService().joinGame(joinGameRequest);
            } catch (BadRequestException e) {
                res.status(400);
                return GSON.toJson(e);
            } catch (AlreadyTakenException e) {
                try {
                    if (ConnectionManager.connections.get(authToken) == null) {
                        joinGameResult = new GameService().joinGameOverride(joinGameRequest);
                        return GSON.toJson(joinGameRequest);
                    }
                } catch (DataAccessException ex) {
                    res.status(500);
                    return GSON.toJson(ex);
                }
                res.status(403);
                return GSON.toJson(e);
            } catch (DataAccessException e) {
                res.status(500);
                return GSON.toJson(e);
            }

            return GSON.toJson(joinGameResult);
        }

        //get game [GET] /play
        public static Object getGame(spark.Request req, spark.Response res) {
            String authToken = req.headers("authorization");
            int gameID = Integer.parseInt(req.params(":gameID"));

            try {
                if (!authenticate(authToken)) {
                    res.status(401);
                    return GSON.toJson(new UnauthorizedException("Error: unauthorized"));
                }
            } catch (DataAccessException e) {
                res.status(500);
                return GSON.toJson(e);
            }

            Request.GetGameRequest getGameRequest = new Request.GetGameRequest(authToken, gameID);
            Result.GetGameResult getGameResult = new GameService().getGame(getGameRequest);
            return new Gson().toJson(getGameResult);
        }
    }

    public static class ClearHandler extends Handler {
        //clear [DELETE] /db
        public static Object clear(spark.Request req, spark.Response res) {
            Request.DeleteRequest deleteRequest = new Request.DeleteRequest();
            Result.DeleteResult deleteResult;
            try {
                deleteResult = new ClearService().clear(deleteRequest);
            } catch (DataAccessException e) {
                res.status(500);
                return GSON.toJson(e);
            }
            res.type("application/json");
            //turn deleteResult to JSON
            return new Gson().toJson(deleteResult);
        }
    }

    private static class ExceptionTypeAdapter implements JsonSerializer<Exception> {
        @Override
        public JsonElement serialize(Exception e, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();
            obj.addProperty("message", e.getMessage());
            return obj;
        }
    }
}
