package service;

import dataaccess.MemoryAuthDAO;

public class AuthService {

    public static boolean authenticate(String authToken) {
        var authDao = new MemoryAuthDAO();

        if (authDao.getAuthData(authToken) == null) {
            return false;
        }
        return true;
    }
}
