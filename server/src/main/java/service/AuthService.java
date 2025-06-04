package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MySqlAuthDAO;

public class AuthService {
    private static final MemoryAuthDAO authDAO = new MemoryAuthDAO();

    public static boolean authenticate(String authToken) throws DataAccessException {
        if (authDAO.getAuthData(authToken) == null) {
            return false;
        }
        return true;
    }
}
