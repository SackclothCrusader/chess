package client;

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
}
