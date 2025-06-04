package dataaccess;

import exceptions.DataAccessException;
import chess.ChessGame;
import model.*;
import org.junit.jupiter.api.Test;

public class SqlTests {
    private MySqlUserDAO userDAO = new MySqlUserDAO();
    private MySqlClearDAO clearDAO = new MySqlClearDAO();
    private MySqlAuthDAO authDAO = new MySqlAuthDAO();
    private MySqlGameDAO gameDAO = new MySqlGameDAO();

    @Test
    public void clearTest() throws DataAccessException {
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
    }

    @Test
    public void negativeAddUserTest() throws DataAccessException{
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
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
    }

    @Test
    public void negativeGetAuthDataTest() throws DataAccessException{
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        UserData user = new UserData("user", "email", "password");
//        AuthData data = authDAO.createAuthData(user);
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
    }

    @Test
    public void positiveAddGameTest() throws DataAccessException{
        System.out.println(gameDAO.createGame("newGame"));
    }

    @Test
    public void negativeAddGameTest() throws DataAccessException{
    }

    @Test
    public void positiveFindGameTest() throws DataAccessException{

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