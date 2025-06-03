package service;

import dataaccess.DataAccessException;
import dataaccess.MySqlClearDAO;
import server.Result;
import server.Request;

public class ClearService {
    private final MySqlClearDAO clearDAO = new MySqlClearDAO();

    public Result.DeleteResult clear(Request.DeleteRequest request) throws DataAccessException{
        try {
            clearDAO.clear();
        } catch (DataAccessException e) {
            throw e;
        }
        return new Result.DeleteResult();
    }
}