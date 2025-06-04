package service;

import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.UnauthorizedException;
import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import server.Request;
import server.Result;

public class UserService {
    private final MemoryUserDAO USER_DAO = new MemoryUserDAO();
    private final MemoryAuthDAO AUTH_DAO = new MemoryAuthDAO();

    //Register
    public Result.RegisterResult register(Request.RegisterRequest request) throws AlreadyTakenException, BadRequestException, DataAccessException {
        if (request == null || request.username() == null || request.password() == null
                || request.username().isEmpty() || request.password().isEmpty() || request.email().isEmpty()) {
            throw new BadRequestException("Error: bad request");
        }
        if (USER_DAO.getUser(request.username()) != null) {
            throw new AlreadyTakenException("Error: already taken");
        }
        UserData user;
        user = USER_DAO.createUser(request.username(), request.email(), request.password());
        AuthData authData = AUTH_DAO.createAuthData(user);
        return new Result.RegisterResult(authData.username(), authData.authToken());
    }

    //Login
    public Result.LoginResult login(Request.LoginRequest request) throws UnauthorizedException, BadRequestException, DataAccessException  {
        if (request == null || request.username() == null || request.password() == null
                || request.password().isBlank() || request.username().isBlank()) {
            throw new BadRequestException("Error: bad request");
        }
        UserData user = USER_DAO.getUser(request.username());
        if (user == null || !BCrypt.checkpw(request.password(), user.password())) {
            throw new UnauthorizedException("Error: unauthorized");
        }
        AuthData authData = AUTH_DAO.createAuthData(user);
        return new Result.LoginResult(authData.username(), authData.authToken());
    }

    //Logout
    public Result.LogoutResult logout(Request.LogoutRequest request) throws DataAccessException{
        AuthData authData = AUTH_DAO.getAuthData(request.authToken());
        AUTH_DAO.deleteAuthData(authData);
        return new Result.LogoutResult();
    }
}