package dataaccess;

import model.AuthData;
import model.UserData;
import java.util.HashSet;
import java.util.UUID;


public interface AuthDao {
    HashSet<AuthData> authDatabase = new HashSet<>();

    //C (make authToken)
    default AuthData makeAuthData(UserData user) {

        return new AuthData("hi", "yup");
    }
    //R (get authToken)
    //U
    //D (delete authToken)


    default String generateToken() {
        return UUID.randomUUID().toString();
    }
}
