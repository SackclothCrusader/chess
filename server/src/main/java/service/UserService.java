package service;

import dataaccess.*;
import server.Request;
import server.Result;

import javax.xml.crypto.Data;
import java.util.List;

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

        return null;
    }

    //Login

    //Logout
}