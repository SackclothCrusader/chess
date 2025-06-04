package service;

import exceptions.DataAccessException;
import dataaccess.*;

public class AuthService {
    private static final MemoryAuthDAO AUTH_DAO = new MemoryAuthDAO();

    public static boolean authenticate(String authToken) throws DataAccessException {
        if (AUTH_DAO.getAuthData(authToken) == null) {
            return false;
        }
        return true;
    }
}
