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

        //This line initializes the server and can be removed once you have a functioning endpoint
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private interface Handler {
        //authorization
//        default boolean authorization(authToken) {}
    }

    private class UserHandler implements Handler {
        //register [POST] /user

        //login [POST] /session

        //logout [DELETE] /session
    }

    private class GameHandler implements Handler {
        //list games [GET] /game

        //create game [POST] /game

        //join game [PUT] /game
    }

    private class ClearHandler implements Handler {
        //clear [DELETE] /db
    }
}
