package server;

import com.google.gson.*;
import dataaccess.AlreadyTakenException;
import dataaccess.BadRequestException;
import server.Request;
import server.Result;
import service.ClearService;
import service.UserService;

public class Handler {
    //authorization
    public boolean authorization(String authToken){
        return false;
    }


    public static class UserHandler extends Handler {
        //register [POST] /user
        public static Object register(spark.Request req, spark.Response res) {
            Request.RegisterRequest registerRequest = new Gson().fromJson(req.body(), Request.RegisterRequest.class);
            try {
                Result.RegisterResult registerResult = new UserService().register(registerRequest);
            } catch (AlreadyTakenException e) {
//                throw new RuntimeException(e);
            } catch (BadRequestException e) {
//                throw new RuntimeException(e);
            }
            return null;
        }

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
            var gson = new Gson().toJson(deleteResult);
            return gson;
        }
    }
}
