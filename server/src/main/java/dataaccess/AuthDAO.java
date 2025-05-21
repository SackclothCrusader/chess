package dataaccess;

import model.AuthData;
import model.UserData;
import java.util.UUID;


public interface AuthDAO {
    //C (make authToken)
    abstract AuthData makeAuthData(UserData user);

    //R (get authToken)
    abstract AuthData getAuthData(UserData user);

    //D (delete authToken)
    abstract void deleteAuthData(AuthData authentication) throws DataAccessException;

    default String generateToken() {
        return UUID.randomUUID().toString();
    }
}
