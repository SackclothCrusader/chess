package client;

import chess.ChessGame;
import org.junit.jupiter.api.*;
import server.Server;
import exceptions.ResponseException;

public class ServerFacadeTests {

    private static Server server;
    private static final String URL = "http://localhost:8080";
    private final ServerFacade facade = new ServerFacade(URL);

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
    }

    @BeforeEach
    public void clear() {
        clearTest();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void clearTest() {
        Assertions.assertDoesNotThrow(()->{facade.clear();});
    }

    @Test
    public void clearEmpty() {
        facade.clear();
        Assertions.assertDoesNotThrow(()->{facade.clear();});
    }

    @Test
    public void posRegisterTest() {
        Assertions.assertDoesNotThrow(() -> {
            facade.register("user", "password", "email");
        });
    }

    @Test
    public void negRegisterTest() {
        facade.register("user", "password", "email");
        Assertions.assertThrows(ResponseException.class, ()->{facade.register("user", "password", "email");});
    }

    @Test
    public void posLoginTest() {
        Assertions.assertDoesNotThrow(() -> {
            facade.register("user", "password", "email");
            facade.login("user", "password");
        });
    }

    @Test
    public void negLoginTest() {
        facade.register("user", "password", "email");
        Assertions.assertThrows(ResponseException.class, ()->{facade.login("user", "BAD_PASSWORD");});
    }

    @Test
    public void posLogoutTest() {
        Assertions.assertDoesNotThrow(() -> {
            String token = facade.register("user", "password", "email");
            facade.logout(token);
            token = facade.login("user", "password");
            facade.logout(token);
        });
    }

    @Test
    public void negLogoutTest() {
        facade.register("user", "password", "email");
        Assertions.assertThrows(ResponseException.class, ()->{facade.logout("not_a_token");});
    }

    @Test
    public void posListGameTest() {
        String token = facade.register("user", "password", "email");
        Assertions.assertDoesNotThrow(()->{facade.listGames(token);});
        System.out.println(facade.listGames(token));
    }

    @Test
    public void negListGameTest() {
        facade.register("user", "password", "email");
        Assertions.assertThrows(ResponseException.class, ()->{facade.listGames("not_a_token");});
    }

    @Test
    public void posAddGameTest() {
        String token = facade.register("user", "password", "email");
        Assertions.assertDoesNotThrow(()->{facade.createGame(token, "Game_1");});
        System.out.println(facade.listGames(token));
    }

    @Test
    public void negAddGameTest() {
        facade.register("user", "password", "email");
        Assertions.assertThrows(ResponseException.class, ()->{facade.createGame("not_a_token", null);});
    }
    @Test
    public void posJoinGameTest() {
        String token = facade.register("user", "password", "email");
        final int gameID = facade.createGame(token, "Game_1");
        System.out.println(facade.listGames(token));
        Assertions.assertDoesNotThrow(()->{facade.joinGame(token, ChessGame.TeamColor.WHITE, gameID);});
        Assertions.assertDoesNotThrow(()->{facade.joinGame(token, ChessGame.TeamColor.BLACK, gameID);});
        System.out.println(facade.listGames(token));
    }

    @Test
    public void negJoinGameTest() {
        String token = facade.register("user", "password", "email");
        final int gameID = facade.createGame(token, "Game_1");
        System.out.println(facade.listGames(token));
        Assertions.assertDoesNotThrow(()->{facade.joinGame(token, ChessGame.TeamColor.WHITE, gameID);});
        Assertions.assertThrows(ResponseException.class, ()->{facade.joinGame(token, ChessGame.TeamColor.WHITE, gameID);});
    }

    @Test
    public void posGetGameTest() {
        String token = facade.register("user", "password", "email");
        final int gameID = facade.createGame(token, "Game_1");
        System.out.println(facade.getGame(token, gameID));
        Assertions.assertDoesNotThrow(()->{facade.joinGame(token, ChessGame.TeamColor.WHITE, gameID);});
        Assertions.assertThrows(ResponseException.class, ()->{facade.joinGame(token, ChessGame.TeamColor.WHITE, gameID);});
    }

    @Test
    public void negGetGameTest() {
        String token = facade.register("user", "password", "email");
        final int gameID = facade.createGame(token, "Game_1");
        System.out.println(facade.listGames(token));
        Assertions.assertThrows(ResponseException.class, ()->{facade.getGame("bad token", gameID);});
    }

    @Test
    public void test1() {}

    @Test
    public void test2() {}

    @Test
    public void test3() {}

    @Test
    public void test4() {}

    @Test
    public void test5() {}

    @Test
    public void test6() {}

    @Test
    public void test7() {}

    @Test
    public void test8() {}
}
