package dataaccess;

import exceptions.DataAccessException;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

public class MySqlUserDAO implements UserDAO {
    public UserData createUser(String username, String email, String password) throws DataAccessException {
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
            throw new DataAccessException("Error: something went wrong", e);
        }
    }

    public UserData getUser(String username) throws DataAccessException {
        try {
            var conn = DatabaseManager.getConnection();

            var statement = "SELECT username, password, email FROM user WHERE username = ?";
            try (var stmt = conn.prepareStatement(statement)) {
                stmt.setString(1, username);

                try (var rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        var user = rs.getString("username");
                        var email = rs.getString("email");
                        var password = rs.getString("password");

                        return new UserData(user, email, password);
                    } else {
                        return null;
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: something went wrong", e);
        }
    }
}
