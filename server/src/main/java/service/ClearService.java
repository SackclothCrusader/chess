package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryClearDAO;
import dataaccess.MySqlClearDAO;
import server.Result;
import server.Request;

public class ClearService {
    private final MemoryClearDAO clearDAO = new MemoryClearDAO();

    public Result.DeleteResult clear(Request.DeleteRequest request) throws DataAccessException{
        try {
            clearDAO.clear();
        } catch (DataAccessException e) {
            throw e;
        }
        return new Result.DeleteResult();
    }
}