package service;

import server.Result;
import server.Request;
import dataaccess.MemoryClearDAO;

public class ClearService {
    public Result.DeleteResult clear(Request.DeleteRequest request) {
        new MemoryClearDAO().clear();
        return new Result.DeleteResult();
    }
}