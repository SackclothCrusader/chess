package service;

import dataaccess.MySqlClearDAO;
import server.Result;
import server.Request;
import dataaccess.MemoryClearDAO;

public class ClearService {
    private final MySqlClearDAO clearDAO = new MySqlClearDAO();

    public Result.DeleteResult clear(Request.DeleteRequest request) {
        clearDAO.clear();
        return new Result.DeleteResult();
    }
}