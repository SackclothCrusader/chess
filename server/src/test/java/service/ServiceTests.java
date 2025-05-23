package service;

import dataaccess.*;
import model.AuthData;
import org.junit.jupiter.api.Test;
import service.*;
import server.Result;
import server.Request;

public class ServiceTests {

    //clear test
    @Test
    public void clearTest() {
        Request.RegisterRequest req = new Request.RegisterRequest("a", "a", "a");
        System.out.println(req);
        try {
            new UserService().register(req);
        } catch (AlreadyTakenException e)
        {
            System.out.println("taken!");
        }
        catch (BadRequestException e)
        {
            System.out.println("empty fields!");
        }
        System.out.println(MemoryUserDAO.getUser(req.username()));
        System.out.println("Clearing!");
        new ClearService().clear(new Request.DeleteRequest());
        System.out.println(MemoryUserDAO.getUser(req.username()));
    }


    //user tests
    @Test
    public void positiveLogoutTest() {
        Request.RegisterRequest req = new Request.RegisterRequest("a", "a", "a");
        System.out.println(req);
        AuthData user;

        try {
            Result.RegisterResult tmp = new UserService().register(req);
            user = new AuthData(tmp.username(), tmp.authToken());
            System.out.println("logout!");
            Request.LogoutRequest logout = new Request.LogoutRequest(user.authToken());
            try {
                new UserService().logout(logout);
            } catch (DataAccessException e) {
                System.out.println("broke");
            }
        } catch (AlreadyTakenException e)
        {
            System.out.println("taken!");
        }
        catch (BadRequestException e)
        {
            System.out.println("empty fields!");
        }

        System.out.println(MemoryUserDAO.getUser(req.username()));
        System.out.println(MemoryUserDAO.getUser(req.username()));
    }

    @Test
    public void negativeLogoutTest() {
        AuthData user = new AuthData("user", "125789");

        System.out.println("logout!");
        Request.LogoutRequest logout = new Request.LogoutRequest(user.authToken());
        try {
            new UserService().logout(logout);
        } catch (DataAccessException e) {
            System.out.println("broke");
        }
    }

    @Test
    public void positiveRegisterTest() {
        Request.RegisterRequest req = new Request.RegisterRequest("a", "a", "a");
        System.out.println(req);
        AuthData user;
        try {
            new UserService().register(req);
        } catch (AlreadyTakenException e)
        {
            System.out.println("taken!");
        }
        catch (BadRequestException e)
        {
            System.out.println("empty fields!");
        }
    }

    @Test
    public void negativeRegisterTest() {
        Request.RegisterRequest req = new Request.RegisterRequest(" ", " ", "a");
        System.out.println(req);
        AuthData user;
        try {
            new UserService().register(req);
        } catch (AlreadyTakenException e)
        {
            System.out.println("taken!");
        }
        catch (BadRequestException e)
        {
            System.out.println("empty fields!");
        }
    }

    @Test
    public void positiveLoginTest() {
        Request.RegisterRequest req = new Request.RegisterRequest("a", "a", "a");
        System.out.println(req);
        AuthData user;

        try {
            Result.RegisterResult tmp = new UserService().register(req);
            user = new AuthData(tmp.username(), tmp.authToken());
            System.out.println("logout!");
            Request.LogoutRequest logout = new Request.LogoutRequest(user.authToken());
            try {
                new UserService().logout(logout);
            } catch (DataAccessException e) {
                System.out.println("broke");
            }
        } catch (AlreadyTakenException e) {
            System.out.println("taken!");
        } catch (BadRequestException e) {
            System.out.println("empty fields!");
        }
        Request.LoginRequest loginRequest = new Request.LoginRequest(req.username(), req.password());
        try {
            new UserService().login(loginRequest);
        } catch (BadRequestException e) {
            System.out.println("empty fields!");
        } catch (UnauthorizedException e) {
            System.out.println("bad password!");
        }
    }

    @Test
    public void negativeLoginTest() {
        Request.RegisterRequest req = new Request.RegisterRequest("a", "a", "a");
        System.out.println(req);
        AuthData user;

        try {
            Result.RegisterResult tmp = new UserService().register(req);
            user = new AuthData(tmp.username(), tmp.authToken());
            System.out.println("logout!");
            Request.LogoutRequest logout = new Request.LogoutRequest(user.authToken());
            try {
                new UserService().logout(logout);
            } catch (DataAccessException e) {
                System.out.println("broke");
            }
        } catch (AlreadyTakenException e) {
            System.out.println("taken!");
        } catch (BadRequestException e) {
            System.out.println("empty fields!");
        }
        Request.LoginRequest loginRequest = new Request.LoginRequest(req.username(), "wrong password");
        try {
            new UserService().login(loginRequest);
        } catch (BadRequestException e) {
            System.out.println("empty fields!");
        } catch (UnauthorizedException e) {
            System.out.println("bad password!");
        }
    }
}
