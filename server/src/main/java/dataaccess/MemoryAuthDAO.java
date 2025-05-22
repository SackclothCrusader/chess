package dataaccess;

import model.AuthData;
import model.UserData;

import java.util.HashSet;

public class MemoryAuthDAO implements AuthDAO {
    public static HashSet<AuthData> authDatabase = new HashSet<>();

    //C (make authToken)
    public AuthData createAuthData(UserData user) {
        return new AuthData(user.username(), generateToken());
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

    //D (delete authToken)
    public void deleteAuthData(AuthData authentication) throws DataAccessException {
        if(!authDatabase.remove(authentication)) {
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
