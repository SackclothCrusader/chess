package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryUserDAO;
import dataaccess.MemoryAuthDAO;
import server.Request;
import server.Result;
import java.util.List;

public class UserService {
    //Register
    public Result.RegisterResult register(String username, String password, String email) {
        if (new MemoryUserDAO().getUser(username) == null) {
            return null;
        }
        return null;
    }

    //Login

    //Logout
}