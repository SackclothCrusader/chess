package dataaccess;

import model.UserData;

import java.util.HashSet;

public class MemoryUserDAO implements UserDAO {
    static HashSet<UserData> users = new HashSet<>();

    //C (make user)
    public UserData createUser(String username, String password, String email) throws DataAccessException{
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
    public static UserData getUser(String username) {
        for (UserData i : users) {
            if (i.username().equals(username)) {
                return i;
            }
        }
        return null;
    }
}
