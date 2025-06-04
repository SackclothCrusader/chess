package dataaccess;

import exceptions.DataAccessException;
import model.AuthData;
import model.UserData;

import java.util.HashSet;

public class MemoryAuthDAO implements AuthDAO {
    static HashSet<AuthData> authDatabase = new HashSet<>();

    //C (make authToken)
    public AuthData createAuthData(UserData user) {
        AuthData tmp = new AuthData(user.username(), generateToken());
        authDatabase.add(tmp);
        return tmp;
    }

    //R (get authToken)
    public AuthData getAuthData(UserData user) {
        for (AuthData i : authDatabase) {
            if (user.username().equals(i.username())) {
                return i;
            }
        }
        return null;
    }

    public AuthData getAuthData(String authToken) {
        for (AuthData i : authDatabase) {
            if (authToken.equals(i.authToken())) {
                return i;
            }
        }
        return null;
    }

    //D (delete authToken)
    public void deleteAuthData(AuthData authentication) throws DataAccessException {
        if(!authDatabase.remove(authentication)) {
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
