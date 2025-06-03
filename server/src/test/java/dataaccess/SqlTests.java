package dataaccess;

import chess.ChessGame;
import model.*;
import org.junit.jupiter.api.Test;

public class SqlTests {
    private MySqlUserDAO userDAO = new MySqlUserDAO();
    private MySqlClearDAO clearDAO = new MySqlClearDAO();
    private MySqlAuthDAO authDAO = new MySqlAuthDAO();
    private MySqlGameDAO gameDAO = new MySqlGameDAO();

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
    public void negativeAddUserTest() {
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        userDAO.createUser(null, "", "");
    }

    @Test
    public void positiveCreateAuthTest() {
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        UserData user = userDAO.createUser("user", "email", "password");
        authDAO.createAuthData(user);
    }

    @Test
    public void negativeCreateAuthTest() {
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        UserData user = new UserData("I", "DONT", "EXIST");
        authDAO.createAuthData(user);
    }

    @Test
    public void positiveGetAuthDataTest() {
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        clearDAO.clear();
        UserData user = userDAO.createUser("user", "email", "password");
        AuthData data = authDAO.createAuthData(user);
        System.out.println(authDAO.getAuthData(user));
    }

    @Test
    public void negativeGetAuthDataTest() {
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        UserData user = new UserData("user", "email", "password");
        AuthData data = authDAO.createAuthData(user);
        System.out.println(authDAO.getAuthData(user));
    }

    @Test
    public void positiveRemoveAuthTest() {
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        clearDAO.clear();
        UserData user = userDAO.createUser("user", "email", "password");
        AuthData data = authDAO.createAuthData(user);
        System.out.println(authDAO.getAuthData(user));
        authDAO.deleteAuthData(data);
        System.out.println(authDAO.getAuthData(user));
    }

    @Test
    public void negativeRemoveAuthTest() {
        try {DatabaseManager.configDatabase();}
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        clearDAO.clear();
        UserData user = userDAO.createUser("user", "email", "password");
        AuthData data = authDAO.createAuthData(user);
        System.out.println(authDAO.getAuthData(user));
        authDAO.deleteAuthData(data);
        authDAO.deleteAuthData(data);
        System.out.println(authDAO.getAuthData(user));
    }

    @Test
    public void positiveAddGameTest() {
        System.out.println(gameDAO.createGame("newGame"));
    }

    @Test
    public void negativeAddGameTest() {
        //System.out.println(gameDAO.createGame(null));
    }

    @Test
    public void positiveFindGameTest() {
        GameData game = gameDAO.createGame("newGame");
        System.out.println(gameDAO.getGame(game.gameID()));
    }

    @Test
    public void positiveUpdatePlayerTest() {
        clearDAO.clear();
        GameData game = gameDAO.createGame("newGame");
        userDAO.createUser("me", "email", "pass");
        userDAO.createUser("you", "email2", "pass");
        gameDAO.addPlayer("me", ChessGame.TeamColor.WHITE, game.gameID());
        gameDAO.addPlayer("you", ChessGame.TeamColor.BLACK, game.gameID());
        System.out.println(gameDAO.getGame(game.gameID()));
    }

    @Test
    public void negativeUpdatePlayerTest() {
        clearDAO.clear();
        GameData game = gameDAO.createGame("newGame");
        userDAO.createUser("me", "email", "pass");
        gameDAO.addPlayer("me", ChessGame.TeamColor.BLACK, game.gameID());
        gameDAO.addPlayer("me", ChessGame.TeamColor.WHITE, game.gameID());
        gameDAO.addPlayer("me", ChessGame.TeamColor.BLACK, game.gameID());
        gameDAO.addPlayer("me", ChessGame.TeamColor.WHITE, game.gameID());
        System.out.println(gameDAO.getGame(game.gameID()));
    }
}
