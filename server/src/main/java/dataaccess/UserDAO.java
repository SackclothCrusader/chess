package dataaccess;

import exceptions.DataAccessException;
import model.UserData;

public interface UserDAO {
    //C (make user)
    abstract UserData createUser(String username, String email, String password) throws DataAccessException;

    //R (find user)
    static UserData getUser(String username) {
        return null;
    }

    //U (change password)

    //D (delete account)
}
