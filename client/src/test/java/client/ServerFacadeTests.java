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


}
