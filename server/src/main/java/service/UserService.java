package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import server.Request;
import server.Result;

public class UserService {
    //Register
    public Result.RegisterResult register(Request.RegisterRequest request) throws AlreadyTakenException, BadRequestException  {
        if (request.username().isEmpty() || request.password().isEmpty() || request.email().isEmpty()) {
            throw new BadRequestException("Error: bad request");
        }
        if (new MemoryUserDAO().getUser(request.username()) != null) {
            throw new AlreadyTakenException("Error: already taken");
        }
        new MemoryUserDAO().createUser(request.username(), request.password(), request.email());
        UserData user = new MemoryUserDAO().getUser(request.username());
        AuthData authData = new MemoryAuthDAO().createAuthData(user);
        return new Result.RegisterResult(authData.username(), authData.authToken());
    }

    //Login

    //Logout
}