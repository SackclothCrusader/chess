package dataaccess;

import exceptions.DataAccessException;

public interface ClearDAO {
    //D (delete all)
    abstract void clear() throws DataAccessException;
}