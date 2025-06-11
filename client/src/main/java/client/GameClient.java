package client;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import model.GameData;
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
                case "quit" -> quit();
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
                - quit
                    End the current session and close 240 Chess client.
               """);
        return false;
    }

    private boolean quit() {
        facade.logout(Repl.AUTH);
        System.out.println("Goodbye :)");
        return true;
    }



    //Printing Board things
    private String[][] emptyBoard(ChessGame.TeamColor perspective) {
        String emptyBoard[][] = new String[10][10];
        String columns[] = {"\u2003a|", "\u2003b|", "\u2003c|", "\u2003d|", "\u2003e|", "\u2003f|", "\u2003g|" , "\u2003h "};
        String border = EscapeSequences.SET_BG_COLOR_BLACK;

        //empty corners
        emptyBoard[0][0] = border + "   ";
        emptyBoard[9][9] = border + "   ";
        emptyBoard[0][9] = border + "   ";
        emptyBoard[9][0] = border + "   ";

        //white
        if (perspective == ChessGame.TeamColor.WHITE) {
            for (int i = 1; i <= 8; i++) {
                emptyBoard[0][i] = border + columns[i - 1];
                emptyBoard[9][i] = border + columns[i - 1];
                emptyBoard[i][0] = border + " " + Integer.toString(9 - i) + " ";
                emptyBoard[i][9] = border + " " + Integer.toString(9 - i) + " ";
            }
        }
        //black
        else {
            for (int i = 1; i <= 8; i++) {
                emptyBoard[0][i] = border + columns[8 - i];
                emptyBoard[9][i] = border + columns[8 - i];
                emptyBoard[i][0] = border + " " + Integer.toString(i) + " ";
                emptyBoard[i][9] = border + " " + Integer.toString(i) + " ";
            }
        }

        //board background
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if ((i + j) % 2 == 0) {
                    emptyBoard[i][j] = EscapeSequences.SET_BG_COLOR_GREEN;
                }
                else {
                    emptyBoard[i][j] = EscapeSequences.SET_BG_COLOR_DARK_GREEN;
                }
            }
        }

        return emptyBoard;
    }

    public void printBoard(int gameID, ChessGame.TeamColor perspective) {
        String[][] board = emptyBoard(perspective);
        ChessBoard game = getGame(Repl.AUTH, gameID).game().getBoard();

        if(perspective == ChessGame.TeamColor.WHITE) {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPiece piece = game.getPiece(new ChessPosition(i, j));
                    board[i][j] += pieceToString(piece);
                }
            }
        }
        else {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPiece piece = game.getPiece(new ChessPosition(i, j));
                    board[9-i][9-j] += pieceToString(piece);
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j =0; j < 10; j++) {
                System.out.print(String.format("%-3s", board[i][j]) + EscapeSequences.RESET_TEXT_COLOR);
            }
            System.out.println(EscapeSequences.RESET_BG_COLOR);
        }
    }

    private GameData getGame(String authToken, int gameID) {
        return facade.getGame(authToken, gameID);
    }

    private String pieceToString (ChessPiece piece) {
        if (piece == null) {
            return EscapeSequences.EMPTY;
        }

        if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            return switch (piece.getPieceType()) {
                case PAWN -> EscapeSequences.SET_TEXT_COLOR_WHITE + EscapeSequences.BLACK_PAWN;
                case KNIGHT -> EscapeSequences.SET_TEXT_COLOR_WHITE + EscapeSequences.BLACK_KNIGHT;
                case BISHOP -> EscapeSequences.SET_TEXT_COLOR_WHITE + EscapeSequences.BLACK_BISHOP;
                case ROOK -> EscapeSequences.SET_TEXT_COLOR_WHITE + EscapeSequences.BLACK_ROOK;
                case KING -> EscapeSequences.SET_TEXT_COLOR_WHITE + EscapeSequences.BLACK_KING;
                case QUEEN -> EscapeSequences.SET_TEXT_COLOR_WHITE + EscapeSequences.BLACK_QUEEN;
            };
        }
        else {
            return switch (piece.getPieceType()) {
                case PAWN -> EscapeSequences.SET_TEXT_COLOR_BLACK + EscapeSequences.BLACK_PAWN;
                case KNIGHT -> EscapeSequences.SET_TEXT_COLOR_BLACK + EscapeSequences.BLACK_KNIGHT;
                case BISHOP -> EscapeSequences.SET_TEXT_COLOR_BLACK + EscapeSequences.BLACK_BISHOP;
                case ROOK -> EscapeSequences.SET_TEXT_COLOR_BLACK + EscapeSequences.BLACK_ROOK;
                case KING -> EscapeSequences.SET_TEXT_COLOR_BLACK + EscapeSequences.BLACK_KING;
                case QUEEN -> EscapeSequences.SET_TEXT_COLOR_BLACK + EscapeSequences.BLACK_QUEEN;
            };
        }
    }
}