package dataaccess;

import model.AuthData;
import model.UserData;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class MySqlAuthDAO implements AuthDAO{
    public AuthData createAuthData(UserData user) {
        return null;
    }

    public AuthData getAuthData(UserData user) {
        return null;
    }

    public void deleteAuthData(AuthData data) {

    }

    private void storeUserPassword(String username, String clearTextPassword) {
        String hashedPassword = BCrypt.hashpw(clearTextPassword, BCrypt.gensalt());
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  authTokens (
              `username` TEXT NOT NULL,
              `email` TEXT NOT NULL,
              `password` TEXT NOT NULL,
              PRIMARY KEY (`username`),
              INDEX(email)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
}
