package client;

import java.util.Scanner;

public class Repl {
    protected static String AUTH;
    private final LoginClient loginClient;
    private final HomeClient homeClient;
    private final GameClient gameClient;

    Repl(String url) {
        loginClient = new LoginClient(url);
        homeClient = new HomeClient(url);
        gameClient = new GameClient(url);
    }

    public void run() {
        loginClient.help();

        Scanner scanner = new Scanner(System.in);

        Boolean quit = false;
        int gameID = -1;
        while(quit) {
            String line = scanner.nextLine();

            if(AUTH.isEmpty()) {
                quit = loginClient.eval(line);
                if (!AUTH.isEmpty()) {
                    quit = false;
                }
            }

            if(gameID < 0) {
                homeClient.eval(line);
            }
        }
    }
}