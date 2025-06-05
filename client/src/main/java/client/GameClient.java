package client;

public class GameClient {
    private ServerFacade facade;

    GameClient(String url) {
        facade = new ServerFacade(url);
    }
}
