package client;

public class HubClient {
    private ServerFacade facade;
    HubClient(String url) {
        facade = new ServerFacade(url);
    }
}
