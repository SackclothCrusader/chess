package dataaccess;

import model.UserData;

import java.util.HashSet;

public class MemoryUserDAO implements UserDAO {
    static HashSet<UserData> users = new HashSet<>();

    //C (make user)
    public UserData createUser(String username, String password, String email){
        UserData user = new UserData(username, email, password);
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
