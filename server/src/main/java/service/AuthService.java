package service;

import dataaccess.MemoryAuthDAO;

public class AuthService {
    public static boolean authenticate(String authToken) {
        if (new MemoryAuthDAO().getAuthData(authToken) == null) {
            return false;
        }
        return true;
    }
}
