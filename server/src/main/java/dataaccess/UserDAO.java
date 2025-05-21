package dataaccess;

import model.UserData;

public interface UserDAO {
    //C (make user)
    abstract UserData createUser(String username, String password, String email) throws DataAccessException;

    //R (find user)
    abstract UserData getUser(String username);

    //U (change password)

    //D (delete account)
}
