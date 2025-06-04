package service;

import exceptions.DataAccessException;
import dataaccess.*;
import server.Result;
import server.Request;

public class ClearService {
    private final MemoryClearDAO CLEAR_DAO = new MemoryClearDAO();

    public Result.DeleteResult clear(Request.DeleteRequest request) throws DataAccessException{
        CLEAR_DAO.clear();
        return new Result.DeleteResult();
    }
}