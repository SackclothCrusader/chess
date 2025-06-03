package dataaccess;

public class MySqlClearDAO implements ClearDAO{
    public void clear() {
        try (var conn = DatabaseManager.getConnection()) {
            try (var stmt = conn.createStatement()) {
                stmt.executeUpdate("DROP TABLE IF EXISTS auth");
                stmt.executeUpdate("DROP TABLE IF EXISTS game");
                stmt.executeUpdate("DROP TABLE IF EXISTS user");
            }
        } catch (Exception e) {
            return;
        }
    }
}