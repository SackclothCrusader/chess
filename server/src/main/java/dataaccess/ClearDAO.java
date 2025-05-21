package dataaccess;

public interface ClearDAO {
    //C
    //R
    //U
    //D (delete all)
    default void clear() {
        GameDAO.games.clear();
        UserDAO.users.clear();
        AuthDao.authDatabase.clear();
    }
}