package dataaccess;

import model.AuthData;
import model.UserData;

public class MySqlAuthDAO implements AuthDAO{
    public AuthData createAuthData(UserData user) {
        AuthData data = new AuthData(user.username(), generateToken());

        try {
            var conn = DatabaseManager.getConnection();
            var statement = "INSERT INTO auth (username, authToken) VALUES (?, ?)";
            try (var stmt = conn.prepareStatement(statement)) {
                stmt.setString(1, data.username());
                stmt.setString(2, data.authToken());

                if (stmt.executeUpdate() == 1) {
                    return data;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AuthData getAuthData(UserData user) {
        try {
            var conn = DatabaseManager.getConnection();

            var statement = "SELECT username, authToken FROM auth WHERE username = ?";
            try (var stmt = conn.prepareStatement(statement)) {
                stmt.setString(1, user.username());

                try (var rs = stmt.executeQuery()) {
                    rs.next();
                    var username = rs.getString("username");
                    var authToken = rs.getString("authToken");

                    return new AuthData(username, authToken);
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    public void deleteAuthData(AuthData data) {
        try {
            var conn = DatabaseManager.getConnection();
            var statement = "DELETE FROM auth WHERE authToken = ?";
            try (var stmt = conn.prepareStatement(statement)) {
                stmt.setString(1, data.authToken());

                stmt.executeUpdate();
            }
        } catch (Exception e) {
            return;
        }
    }
}
