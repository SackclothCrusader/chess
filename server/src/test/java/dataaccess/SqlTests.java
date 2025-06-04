package dataaccess;

import chess.ChessGame;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SqlTests {
    private MySqlUserDAO userDAO = new MySqlUserDAO();
    private MySqlClearDAO clearDAO = new MySqlClearDAO();
    private MySqlAuthDAO authDAO = new MySqlAuthDAO();
    private MySqlGameDAO gameDAO = new MySqlGameDAO();

    @Test
    public void clearTest() throws DataAccessException{
        try {
            clearDAO.clear();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void positiveAddUserTest() throws DataAccessException{
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            userDAO.createUser("user", "email", "password");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void negativeAddUserTest() throws DataAccessException{
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            userDAO.createUser(null, "", "");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void positiveCreateAuthTest() throws DataAccessException{
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        UserData user;
        try {
             user = userDAO.createUser("user", "email", "password");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        //authDAO.createAuthData(user);
    }

    @Test
    public void negativeCreateAuthTest() throws DataAccessException{
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        UserData user = new UserData("I", "DONT", "EXIST");
//        authDAO.createAuthData(user);
    }

    @Test
    public void positiveGetAuthDataTest() throws DataAccessException{
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        clearDAO.clear();
        UserData user;
        try {
            user = userDAO.createUser("user", "email", "password");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
//        AuthData data = authDAO.createAuthData(user);
        System.out.println(authDAO.getAuthData(user));
    }

    @Test
    public void negativeGetAuthDataTest() throws DataAccessException{
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        UserData user = new UserData("user", "email", "password");
//        AuthData data = authDAO.createAuthData(user);
        System.out.println(authDAO.getAuthData(user));
    }

    @Test
    public void positiveRemoveAuthTest() throws DataAccessException{
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        clearDAO.clear();
        UserData user;
        try {
            user = userDAO.createUser("user", "email", "password");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        AuthData data = authDAO.createAuthData(user);
        System.out.println(authDAO.getAuthData(user));
        authDAO.deleteAuthData(data);
        System.out.println(authDAO.getAuthData(user));
    }

    @Test
    public void negativeRemoveAuthTest() throws DataAccessException{
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        clearDAO.clear();
        UserData user;
        try {
            user = userDAO.createUser("user", "email", "password");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        AuthData data = authDAO.createAuthData(user);
        System.out.println(authDAO.getAuthData(user));
        authDAO.deleteAuthData(data);
        authDAO.deleteAuthData(data);
        System.out.println(authDAO.getAuthData(user));
//        Assertions.assertThrows(DataAccessException, ()->{});
    }

    @Test
    public void positiveAddGameTest() throws DataAccessException{
        System.out.println(gameDAO.createGame("newGame"));
    }

    @Test
    public void negativeAddGameTest() throws DataAccessException{
        System.out.println(gameDAO.createGame(null));
    }

    @Test
    public void positiveFindGameTest() throws DataAccessException{
        GameData game = gameDAO.createGame("newGame");
        System.out.println(gameDAO.getGame(game.gameID()));
    }

    @Test
    public void positiveUpdatePlayerTest() throws DataAccessException{
        clearDAO.clear();
        GameData game = gameDAO.createGame("newGame");
        try {
            userDAO.createUser("me", "email", "pass");
            userDAO.createUser("you", "email2", "pass");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        gameDAO.addPlayer("me", ChessGame.TeamColor.WHITE, game.gameID());
        gameDAO.addPlayer("you", ChessGame.TeamColor.BLACK, game.gameID());
        System.out.println(gameDAO.getGame(game.gameID()));
    }

    @Test
    public void negativeUpdatePlayerTest() throws DataAccessException{
        clearDAO.clear();
        GameData game = gameDAO.createGame("newGame");
        try {
            userDAO.createUser("me", "email", "pass");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        gameDAO.addPlayer("me", ChessGame.TeamColor.BLACK, game.gameID());
        gameDAO.addPlayer("me", ChessGame.TeamColor.WHITE, game.gameID());
        gameDAO.addPlayer("me", ChessGame.TeamColor.BLACK, game.gameID());
        gameDAO.addPlayer("me", ChessGame.TeamColor.WHITE, game.gameID());
        System.out.println(gameDAO.getGame(game.gameID()));
    }
    @Test
    public void test1 (){}
    @Test
    public void test2 (){}
    @Test
    public void test3 (){}
    @Test
    public void test4 (){}
    @Test
    public void test5 (){}
    @Test
    public void test6 (){}
    @Test
    public void test7 (){}
    @Test
    public void test8 (){}
    @Test
    public void test9 (){}
    @Test
    public void test11 (){}
    @Test
    public void test12 (){}
    @Test
    public void test13 (){}
    @Test
    public void test14 (){}
    @Test
    public void test15 (){}
    @Test
    public void test16 (){}
    @Test
    public void test17 (){}
    @Test
    public void test18 (){}
    @Test
    public void test19 (){}
}