package client;

import java.util.Scanner;

public class Repl {
    protected static String AUTH;
    protected static int GAME_ID;
    private final LoginClient loginClient;
    private final HomeClient homeClient;
    private final GameClient gameClient;

    public Repl(String url) {
        AUTH = "";
        GAME_ID = 0;
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

            if(AUTH.isEmpty()) {
                quit = loginClient.eval(line);
            }
            else if(GAME_ID <= 0) {
                quit = homeClient.eval(line);
            }
            else if(GAME_ID > 0) {
                quit = gameClient.eval(line);
            }
        }
    }
}