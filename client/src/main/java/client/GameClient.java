package client;

import chess.*;
import model.GameData;
import ui.EscapeSequences;

import java.util.Collection;
import java.util.HashSet;

public class GameClient {
    private static ServerFacade facade;

    GameClient(String url) {
        facade = new ServerFacade(url);
    }

    public boolean eval(String in) {
        var tokens = in.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "";

        if (cmd.equals("make")) {
            cmd = tokens[0] + " " + tokens[1];
        }
        else if (cmd.equals("redraw") || cmd.equals("highlight")) {
            cmd = tokens[0] + " " + tokens[1] + " " + tokens[2];
        }

        try {
            switch (cmd) {
                case "help" -> help();
                case "redraw chess board" -> redraw();
                case "leave" -> exit();
                case "make move" -> move(tokens[2], tokens[3], tokens[4]);
                case "resign" -> resign();
                case "highlight legal moves" -> legal(tokens[3]);
                default -> System.out.println("Unknown command. Type help to open the help menu.");
            };
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Incorrect number of arguments for " + cmd + "\nType help to open the help menu.");
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect argument type for " + cmd + "\nType help to open the help menu.");
        } catch (Exception e) {
            System.out.println("There was an error with command " + cmd + "\nType help to open the help menu.");
        }
        return false;
    }

    public void help() {
        System.out.println("""
                Welcome to the game menu! To show this menu type help.
               
                Commands:
                - help
                    Display this page.
                -redraw chess board
                    Draws the game board.
                -leave
                    Return to client home.
                - make move <start position ([a-h][1-8])> <end position ([a-h][1-8])>
                    Moves a piece from starting position to end position.
                - resign
                    Resign the game. Does not leave the game.
                - highlight legal moves <position ([a-h][1-8])>
                    Highlights legal moves on the board.
               """);
    }

    private void redraw() {
        String[][] board = fillBoard(Repl.gameID, Repl.teamcolor);
        printBoard(board);
    }

    private void exit() {
        Repl.gameID = 0;
        //add ws
        System.out.println("Returning to home.");
    }

    private void move(String startPos, String endPos, String promo) {
        ChessMove move = new ChessMove(stringToPos(startPos), stringToPos(endPos), strToPromote(promo));
        //add ws
    }

    private void resign() {
        //add ws
    }

    private void legal(String origin) {
        String[][] board = setHighlight(stringToPos(origin));
        printBoard(board);
    }


    public static GameData getGame(String authToken, int gameID) {
        return facade.getGame(authToken, gameID);
    }

    //Printing Board things

    private static String[][] emptyBoard(ChessGame.TeamColor perspective) {
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

    private static String[][] fillBoard(int gameID, ChessGame.TeamColor perspective) {
        String[][] board = emptyBoard(perspective);
        ChessBoard game = getGame(Repl.auth, gameID).game().getBoard();

        if(perspective == ChessGame.TeamColor.WHITE) {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPiece piece = game.getPiece(new ChessPosition(i, j));
                    board[9-i][9-j] += pieceToString(piece);
                }
            }
        }
        else {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPiece piece = game.getPiece(new ChessPosition(i, j));
                    board[i][j] += pieceToString(piece);
                }
            }
        }

        return board;
    }

    private static String[][] setHighlight(ChessPosition origin) {
        //init
        String[][] board = fillBoard(Repl.gameID, Repl.teamcolor);
        ChessGame game = getGame(Repl.auth, Repl.gameID).game();
        Collection<ChessMove> validMoves = game.validMoves(origin);
        Collection<ChessPosition> endLocations = new HashSet<>();
        for (ChessMove move : validMoves) {
            endLocations.add(move.getEndPosition());
        }

        //set highlight
        for (ChessPosition position : endLocations) {
            int row = (Repl.teamcolor == ChessGame.TeamColor.WHITE) ? (9 - position.getRow()) : (position.getRow());
            int col = (Repl.teamcolor == ChessGame.TeamColor.BLACK) ? (9 - position.getColumn()) : (position.getColumn());

            board[row][col] = EscapeSequences.SET_BG_COLOR_RED +
                    pieceToString(game.getBoard().getPiece(position));
        }

        return board;
    }

    private ChessPosition stringToPos(String in) throws IllegalArgumentException{
        //init
        char toCol = in.charAt(0);
        char toRow = in.charAt(1);
        int col;
        int row;

        //convert
        switch (toCol) {
            case 'a' -> col = 1;
            case 'b' -> col = 2;
            case 'c' -> col = 3;
            case 'd' -> col = 4;
            case 'e' -> col = 5;
            case 'f' -> col = 6;
            case 'g' -> col = 7;
            case 'h' -> col = 8;
            default -> throw new IllegalArgumentException();
        };
        row = Integer.parseInt(String.valueOf(toRow));

        //safety
        if (row < 1 || row > 8) {
            throw new IllegalArgumentException();
        }

        return new ChessPosition(row, col);
    }

    private static void printBoard(String[][] board) {
        System.out.println(EscapeSequences.ERASE_SCREEN);

        for (int i = 0; i < 10; i++) {
            for (int j =0; j < 10; j++) {
                System.out.print(String.format("%-3s", board[i][j]) + EscapeSequences.RESET_TEXT_COLOR);
            }
            System.out.println(EscapeSequences.RESET_BG_COLOR);
        }
    }

    private static ChessPiece.PieceType strToPromote(String piece) throws IllegalArgumentException{
        return switch (piece) {
            case "knight" -> ChessPiece.PieceType.KNIGHT;
            case "n" -> ChessPiece.PieceType.KNIGHT;
            case "bishop" -> ChessPiece.PieceType.BISHOP;
            case "b" -> ChessPiece.PieceType.BISHOP;
            case "rook" -> ChessPiece.PieceType.ROOK;
            case "r" -> ChessPiece.PieceType.ROOK;
            case "queen" -> ChessPiece.PieceType.QUEEN;
            case "q" -> ChessPiece.PieceType.QUEEN;
            default -> throw new IllegalArgumentException();
        };
    }


    private static String pieceToString (ChessPiece piece) {
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