package server;

import spark.*;

public class Server {
    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        //clear
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
