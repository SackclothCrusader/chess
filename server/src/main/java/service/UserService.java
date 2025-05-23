package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import server.Request;
import server.Result;

public class UserService {
    //Register
    public Result.RegisterResult register(Request.RegisterRequest request) throws AlreadyTakenException, BadRequestException  {
        if (request == null || request.username() == null || request.username() == null || request.password() == null
                || request.username().isEmpty() || request.password().isEmpty() || request.email().isEmpty()) {
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
    public Result.LoginResult login(Request.LoginRequest request) throws UnauthorizedException, BadRequestException  {
        if (request == null || request.username() == null || request.password() == null
                || request.password().isBlank() || request.username().isBlank()) {
            throw new BadRequestException("Error: bad request");
        }
        UserData user = MemoryUserDAO.getUser(request.username());
        if (user == null || !request.password().equals(user.password())) {
            throw new UnauthorizedException("Error: unauthorized");
        }
        AuthData authData = new MemoryAuthDAO().createAuthData(user);
        return new Result.LoginResult(authData.username(), authData.authToken());
    }

    //Logout
    public Result.LogoutResult logout(Request.LogoutRequest request) throws DataAccessException{
        AuthData authData = new MemoryAuthDAO().getAuthData(request.authToken());
        try {
            new MemoryAuthDAO().deleteAuthData(authData);
        } catch (DataAccessException e) {
            throw e;
        }
        return new Result.LogoutResult();
    }
}