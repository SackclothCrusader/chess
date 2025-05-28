package service;

import server.Result;
import server.Request;
import dataaccess.MemoryClearDAO;

public class ClearService {
    public Result.DeleteResult clear(Request.DeleteRequest request) {
        var clearDAO = new MemoryClearDAO();

        clearDAO.clear();
        return new Result.DeleteResult();
    }
}