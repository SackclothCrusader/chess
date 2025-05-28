package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import server.Request;
import server.Result;

public class UserService {
    private final MemoryUserDAO userDAO = new MemoryUserDAO();
    private final MemoryAuthDAO authDAO = new MemoryAuthDAO();

    //Register
    public Result.RegisterResult register(Request.RegisterRequest request) throws AlreadyTakenException, BadRequestException  {
        if (request == null || request.username() == null || request.password() == null
                || request.username().isEmpty() || request.password().isEmpty() || request.email().isEmpty()) {
            throw new BadRequestException("Error: bad request");
        }
        if (userDAO.getUser(request.username()) != null) {
            throw new AlreadyTakenException("Error: already taken");
        }
        userDAO.createUser(request.username(), request.password(), request.email());
        UserData user = userDAO.getUser(request.username());
        AuthData authData = authDAO.createAuthData(user);
        return new Result.RegisterResult(authData.username(), authData.authToken());
    }

    //Login
    public Result.LoginResult login(Request.LoginRequest request) throws UnauthorizedException, BadRequestException  {
        if (request == null || request.username() == null || request.password() == null
                || request.password().isBlank() || request.username().isBlank()) {
            throw new BadRequestException("Error: bad request");
        }
        UserData user = userDAO.getUser(request.username());
        if (user == null || !request.password().equals(user.password())) {
            throw new UnauthorizedException("Error: unauthorized");
        }
        AuthData authData = authDAO.createAuthData(user);
        return new Result.LoginResult(authData.username(), authData.authToken());
    }

    //Logout
    public Result.LogoutResult logout(Request.LogoutRequest request) throws DataAccessException{
        AuthData authData = authDAO.getAuthData(request.authToken());
        try {
            authDAO.deleteAuthData(authData);
        } catch (DataAccessException e) {
            throw e;
        }
        return new Result.LogoutResult();
    }
}