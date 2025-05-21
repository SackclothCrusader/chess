package service;

import dataaccess.UserDAO;
import dataaccess.AuthDao;
import server.Request;
import server.Result;
import java.util.List;

public class UserService {
    //Register
    public Result.RegisterResult register(String username, String password, String email) {
        if (UserDAO.getUser(username)) {

        }
        return null;
    }

    //Login

    //Logout

}
