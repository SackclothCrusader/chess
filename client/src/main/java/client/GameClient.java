package client;

import chess.ChessGame;
import ui.EscapeSequences;

public class GameClient {
    private ServerFacade facade;

    GameClient(String url) {
        facade = new ServerFacade(url);
    }

    public boolean eval(String in) {
        printBoard(Repl.GAME_ID, ChessGame.TeamColor.BLACK);

        var tokens = in.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "";
        try {
            return switch (cmd) {
                case "register" -> throw new RuntimeException();
                default -> help();
            };
        } catch (Exception e) {
            return help();
        }
    }

    public boolean help() {
        System.out.println("""
                Welcome to the game menu! To show this menu type help.
               
                Commands:
                - help
                    Display this page.
                - logout
                    Logout current user and return to login.
                - create game
                    Creates a new game. Note that you must join the game manually.
                - list games
                    Lists all games in the database.
                - play game <gameID> <BLACK/WHITE>
                    Allows the user to join a game as black or white and play.
                - observe game <gameID>
                    Spectate a game.
                - quit
                    End the current session and close 240 Chess client.
               """);
        return false;
    }

    private String emptyBoard(int gameID, ChessGame.TeamColor perspective) {
        System.out.print(EscapeSequences.ERASE_SCREEN);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boolean isDark = (row + col) % 2 == 1;
                String bgColor = isDark ? EscapeSequences.SET_BG_COLOR_DARK_GREY : EscapeSequences.SET_BG_COLOR_LIGHT_GREY;

                // Print an empty space on a colored square
                System.out.print(bgColor + " " + EscapeSequences.RESET_BG_COLOR);
            }
            System.out.println(); // Newline at end of row
        }

        System.out.print(EscapeSequences.RESET_TEXT_COLOR + EscapeSequences.RESET_BG_COLOR);
    }
}