package dataaccess;

import model.AuthData;
import model.UserData;
import java.util.HashSet;
import java.util.UUID;


public interface AuthDao {
    HashSet<AuthData> authDatabase = new HashSet<>();

    //C (make authToken)
    default AuthData makeAuthData(UserData user) {
        return new AuthData(user.username(), generateToken());
    }

    //R (get authToken)
    default AuthData getAuthData(UserData user) {
        for (AuthData i : authDatabase) {
            if (user.username().equals(i.username())) {
                return i;
            }
        }
        return null;
    }

    //D (delete authToken)
    default void deleteAuthData(AuthData authentication) throws DataAccessException {
        if(!authDatabase.remove(authentication)) {
            throw new DataAccessException("Error: unauthorized");
        }
    }


    default String generateToken() {
        return UUID.randomUUID().toString();
    }
}
