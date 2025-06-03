package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import com.google.gson.*;
import java.sql.*;

public class MySqlUserDAO implements UserDAO {
    public void example() throws Exception {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT 1+1")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
                System.out.println(rs.getInt(1));
            }
        }
    }

    public UserData createUser(String username, String email, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        UserData data = new UserData(username, email, hashedPassword);

        try {
            var conn = DatabaseManager.getConnection();
            var statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
            try (var stmt = conn.prepareStatement(statement)) {
                stmt.setString(1, data.username());
                stmt.setString(2, data.password());
                stmt.setString(3, data.email());

                if (stmt.executeUpdate() == 1) {
                    return data;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static UserData getUser(String username) {
        try {
            var conn = DatabaseManager.getConnection();

            var statement = "SELECT username, password, email FROM user WHERE username = ?";
            try (var stmt = conn.prepareStatement(statement)) {
                stmt.setString(1, username);

                try (var rs = stmt.executeQuery()) {
                    rs.next();
                    var user = rs.getString("username");
                    var password = rs.getString("password");
                    var email = rs.getString("email");

                    return new UserData(user, email, password);
                }
            }
        } catch (Exception e) {
            return null;
        }
    }
}
