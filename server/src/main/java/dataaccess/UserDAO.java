package dataaccess;

import model.UserData;
import java.util.HashSet;

public interface UserDAO {
    HashSet<UserData> users = new HashSet<>();

    //C (make user)
    default UserData createUser(String username, String password, String email) throws DataAccessException{
        for (UserData i : users) {
            if (i.username().equals(username) || i.email().equals(email)) {
                throw new DataAccessException("Error: already taken");
            }
        }

        UserData user = new UserData(username, password, email);
        users.add(user);
        return user;
    }

    //R (find user)
    default UserData getUser(String username) {
        for (UserData i : users) {
            if (i.username().equals(username)) {
                return i;
            }
        }
        return null;
    }

    //U (change password)

    //D (delete account)
}
