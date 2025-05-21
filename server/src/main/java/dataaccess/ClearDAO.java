package dataaccess;

public interface ClearDAO {
    //D (delete all)
    default void clear() {
        GameDAO.games.clear();
        UserDAO.users.clear();
        AuthDao.authDatabase.clear();
    }
}