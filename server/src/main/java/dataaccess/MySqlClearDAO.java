package dataaccess;

import exceptions.DataAccessException;

public class MySqlClearDAO implements ClearDAO{
    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var stmt = conn.createStatement()) {
                stmt.executeUpdate("DROP TABLE IF EXISTS auth");
                stmt.executeUpdate("DROP TABLE IF EXISTS game");
                stmt.executeUpdate("DROP TABLE IF EXISTS user");
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: something went wrong", e);
        }
        try {
            DatabaseManager.configDatabase();
        } catch (DataAccessException e) {
            throw e;
        }
    }
}