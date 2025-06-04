package dataaccess;

import exceptions.DataAccessException;
import model.AuthData;
import model.UserData;
import java.util.UUID;


public interface AuthDAO {
    //C (make authToken)
    abstract AuthData createAuthData(UserData user) throws DataAccessException;

    //R (get authToken)
    abstract AuthData getAuthData(UserData user) throws DataAccessException;

    //D (delete authToken)
    abstract void deleteAuthData(AuthData authentication) throws DataAccessException;

    default String generateToken() {
        return UUID.randomUUID().toString();
    }
}
