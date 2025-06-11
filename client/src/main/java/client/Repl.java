package client;

import java.util.Scanner;

public class Repl {
    protected static String auth;
    protected static int gameID;
    private final LoginClient loginClient;
    private final HomeClient homeClient;
    private final GameClient gameClient;

    public Repl(String url) {
        auth = "";
        gameID = 0;
        loginClient = new LoginClient(url);
        homeClient = new HomeClient(url);
        gameClient = new GameClient(url);
    }

    public void run() {
        loginClient.help();
        Scanner scanner = new Scanner(System.in);
        Boolean quit = false;

        while(!quit) {
            System.out.print("> ");
            String line = scanner.nextLine();

            if(auth.isEmpty()) {
                quit = loginClient.eval(line);
            }
            else if(gameID <= 0) {
                quit = homeClient.eval(line);
            }
            else if(gameID > 0) {
                quit = gameClient.eval(line);
            }
        }
    }
}