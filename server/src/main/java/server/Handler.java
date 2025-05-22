package server;

import com.google.gson.*;
import server.Request;
import server.Result;
import service.ClearService;

public class Handler {
    //authorization
    public boolean authorization(String authToken){
        return false;
    }


    public static class UserHandler extends Handler {
        //register [POST] /user

        //login [POST] /session

        //logout [DELETE] /session
    }

    public static class GameHandler extends Handler {
        //list games [GET] /game

        //create game [POST] /game

        //join game [PUT] /game
    }

    public static class ClearHandler extends Handler {
        //clear [DELETE] /db
        public static Object clear(spark.Request req, spark.Response res) {
            Request.DeleteRequest deleteRequest = new Request.DeleteRequest();
            Result.DeleteResult deleteResult = new ClearService().clear(deleteRequest);
            res.type("application/json");
            //turn deleteResult to JSON
            var gson = new Gson().toJson(deleteRequest);
            return gson;
        }
    }
}
