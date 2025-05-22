package service;

import dataaccess.AlreadyTakenException;
import dataaccess.BadRequestException;
import dataaccess.DataAccessException;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import org.junit.jupiter.api.Test;
import service.*;
import server.Result;
import server.Request;

public class ServiceTests {
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

    @Test
    public void logoutTest() {
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
}
