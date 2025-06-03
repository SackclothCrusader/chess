package service;

import dataaccess.MySqlAuthDAO;

public class AuthService {
    private static final MySqlAuthDAO authDAO = new MySqlAuthDAO();

    public static boolean authenticate(String authToken) {
        if (authDAO.getAuthData(authToken) == null) {
            return false;
        }
        return true;
    }
}
