package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;

public class MemoryUserDAO implements UserDAO {
    static HashSet<UserData> users = new HashSet<>();

    //C (make user)
    public UserData createUser(String username, String email, String password){
        String hashWord = BCrypt.hashpw(password, BCrypt.gensalt());

        UserData user = new UserData(username, email, hashWord);
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
