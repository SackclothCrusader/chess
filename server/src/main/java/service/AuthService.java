package service;

import exceptions.DataAccessException;
import dataaccess.*;

public class AuthService {
    private static final MySqlAuthDAO AUTH_DAO = new MySqlAuthDAO();

    public static boolean authenticate(String authToken) throws DataAccessException {
        if (AUTH_DAO.getAuthData(authToken) == null) {
            return false;
        }
        return true;
    }
}
