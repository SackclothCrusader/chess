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
        String border = EscapeSequences.SET_BG_COLOR_LIGHT_GREY;

        emptyBoard[0][0] = border + " ";
        emptyBoard[9][9] = border + " ";
        emptyBoard[0][9] = border + " ";
        emptyBoard[9][0] = border + " ";

        //white
        if (perspective == ChessGame.TeamColor.WHITE) {
            for (int i = 1; i <= 8; i++) {
                emptyBoard[0][i] = border + columns[i-1];
                emptyBoard[9][i] = border + columns[i-1];
                emptyBoard[i][0] = border + Integer.toString(9-i);
                emptyBoard[i][9] = border + Integer.toString(9-i);
            }
        }
        //black
        else {
            for (int i = 1; i <= 8; i++) {
                emptyBoard[0][i] = border + columns[8-i];
                emptyBoard[9][i] = border + columns[8-i];
                emptyBoard[i][0] = border + Integer.toString(i);
                emptyBoard[i][9] = border + Integer.toString(i);
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

    private String[][] fillBoard(String[][] board, int gameID) {
        return board;
    }

    private void printBoard(int gameID, ChessGame.TeamColor perspective) {
        String[][] board = emptyBoard(perspective);


        // Loop through all rows
        for (int i = 0; i < board.length; i++) {
            // Loop through columns in the current row
            for (int j = 0; j < board[i].length; j++) {
                String cell = board[i][j];

                // If cell is null, print empty 3 spaces
                if (cell == null) {
                    System.out.print("   ");
                } else {
                    // Print the cell content
                    System.out.print(cell);
                }

                // Reset colors after each cell to avoid color bleeding
                System.out.print(EscapeSequences.RESET_BG_COLOR);
            }
            System.out.println(); // New line at end of row
        }
    }
}