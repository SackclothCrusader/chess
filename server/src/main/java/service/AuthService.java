package service;

import dataaccess.DataAccessException;
import dataaccess.MySqlAuthDAO;

public class AuthService {
    private static final MySqlAuthDAO authDAO = new MySqlAuthDAO();

    public static boolean authenticate(String authToken) throws DataAccessException {
        if (authDAO.getAuthData(authToken) == null) {
            return false;
        }
        return true;
    }
}
