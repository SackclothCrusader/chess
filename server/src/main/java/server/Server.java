package server;

import server.websocket.WSHandler;
import spark.*;
import dataaccess.DatabaseManager;

public class Server {
    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        try {
            DatabaseManager.configDatabase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        //Websocket
        Spark.webSocket("/ws", WSHandler.class);

        //Clear
        Spark.delete("/db", (req, res) -> Handler.ClearHandler.clear(req, res));

        //Register
        Spark.post("/user", (req, res) -> Handler.UserHandler.register(req, res));

        //Login
        Spark.post("/session", (req, res) -> Handler.UserHandler.login(req, res));

        //Logout
        Spark.delete("/session", (req, res) -> Handler.UserHandler.logout(req, res));

        //List Games
        Spark.get("/game", (req, res) -> Handler.GameHandler.listGames(req, res));

        //Create Game
        Spark.post("/game", (req, res) -> Handler.GameHandler.createGame(req, res));

        //Join
        Spark.put("/game", (req, res) -> Handler.GameHandler.joinGame(req, res));

        //Get Game
        Spark.get("/play/:gameID", (req, res) -> Handler.GameHandler.getGame(req, res));

        //Update Game
//        Spark.put("/play", (req, res) -> Handler.GameHandler.updateGame(req, res));

        //This line initializes the server and can be removed once you have a functioning endpoint
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
