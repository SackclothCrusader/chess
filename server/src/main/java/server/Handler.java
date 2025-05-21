package server;

public class Handler {
    //authorization
//        default boolean authorization(authToken) {}

    private class UserHandler extends Handler {
        //register [POST] /user

        //login [POST] /session

        //logout [DELETE] /session
    }

    private class GameHandler extends Handler {
        //list games [GET] /game

        //create game [POST] /game

        //join game [PUT] /game
    }

    private class ClearHandler extends Handler {
        //clear [DELETE] /db
    }
}
