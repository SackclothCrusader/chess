package dataaccess;

public class MemoryClearDAO implements ClearDAO{
    public void clear() {
        MemoryGameDAO.games.clear();
        MemoryUserDAO.users.clear();
        MemoryAuthDAO.authDatabase.clear();
    }
}