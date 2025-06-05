package client;

public class Repl {
    protected static String AUTH;
    private final LoginClient loginClient;
    private final HubClient homeClient;
    private final GameClient gameClient;
    Repl(String url) {
        loginClient = new LoginClient(url);
        homeClient = new HubClient(url);
        gameClient = new GameClient(url);
    }

    public void run() {

    }
}