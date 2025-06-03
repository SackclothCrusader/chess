package dataaccess;

import dataaccess.MySqlUserDAO;
import org.junit.jupiter.api.Test;

public class SqlTests {
    private MySqlUserDAO userDAO = new MySqlUserDAO();
    private MySqlClearDAO clearDAO = new MySqlClearDAO();

    @Test
    public void clearTest() {
        try {
            clearDAO.clear();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void positiveAddUserTest() {
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        userDAO.createUser("user", "email", "password");
    }

    @Test
    public void negativeRegisterTest() {

    }

    @Test
    public void positiveLoginTest() {

    }

    @Test
    public void negativeLoginTest() {

    }

    @Test
    public void positiveLogoutTest() {

    }

    @Test
    public void negativeLogoutTest() {

    }

    @Test
    public void positiveCreateGameTest() {

    }

    @Test
    public void negativeCreateGameTest() {

    }

    @Test
    public void positiveListGamesTest() {

    }

    @Test
    public void negativeListGamesTest() {

    }

    @Test
    public void positiveJoinGameTest() {

    }

    @Test
    public void negativeJoinGameTest() {

    }
}
