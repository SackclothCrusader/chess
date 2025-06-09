package client;

import chess.ChessGame;
import ui.EscapeSequences;

public class GameClient {
    private ServerFacade facade;
    GameClient(String url) {
        facade = new ServerFacade(url);
    }

    public boolean eval(String in) {
        System.out.println("Printing black:");
        printBoard(Repl.GAME_ID, ChessGame.TeamColor.BLACK);
        System.out.println("Printing white:");
        printBoard(Repl.GAME_ID, ChessGame.TeamColor.WHITE);

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

    private String[][] emptyBoard(ChessGame.TeamColor perspective) {
        String emptyBoard[][] = new String[10][10];
        String columns[] = {"a", "b", "c", "d", "e", "f", "g", "h"};

        emptyBoard[0][0] = "0";
        emptyBoard[9][9] = "0";
        emptyBoard[0][9] = "0";
        emptyBoard[9][0] = "0";

        //white
        if (perspective == ChessGame.TeamColor.WHITE) {
            for (int i = 1; i <= 8; i++) {
                emptyBoard[0][i] = columns[i-1];
                emptyBoard[9][i] = columns[i-1];
                emptyBoard[i][0] = Integer.toString(9-i);
                emptyBoard[i][9] = Integer.toString(9-i);
            }
        }
        //black
        else {
            for (int i = 1; i <= 8; i++) {
                emptyBoard[0][i] = columns[8-i];
                emptyBoard[9][i] = columns[8-i];
                emptyBoard[i][0] = Integer.toString(i);
                emptyBoard[i][9] = Integer.toString(i);
            }
        }

        //background
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if ((i + j) % 2 == 0) {
                    emptyBoard[i][j] = EscapeSequences.SET_BG_COLOR_WHITE;
                }
                else {
                    emptyBoard[i][j] = EscapeSequences.SET_BG_COLOR_BLACK;
                }
            }
        }

        return emptyBoard;
    }

    private void printBoard(int gameID, ChessGame.TeamColor perspective) {
        String[][] board = emptyBoard(perspective);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j] + "   " + EscapeSequences.RESET_BG_COLOR);
            }
            System.out.println();
        }
    }
}