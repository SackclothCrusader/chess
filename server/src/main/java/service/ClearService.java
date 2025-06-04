package service;

import dataaccess.DataAccessException;
import dataaccess.*;
import server.Result;
import server.Request;

public class ClearService {
    private final MemoryClearDAO clearDAO = new MemoryClearDAO();

    public Result.DeleteResult clear(Request.DeleteRequest request) throws DataAccessException{
        clearDAO.clear();
        return new Result.DeleteResult();
    }
}