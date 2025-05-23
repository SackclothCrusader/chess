package chess;

import java.util.ArrayList;

public interface MoveCalc {
    abstract ArrayList<ChessMove> moveCalc(ChessBoard board, ChessPosition start);

    // return true if the square is passable
    default boolean AddValidMove(ChessBoard board, ChessPosition start, ChessPosition end, ArrayList<ChessMove> moves) {
        //ensure existence of base piece
        ChessPiece startPiece = board.getPiece(start);
        if (startPiece == null) {
            return false;
        }
        // on the board (rows)
        if (!(1 <= end.getRow() && end.getRow() <= 8)) {
            return false;
        }
        // on the board (cols)
        if (!(1 <= end.getColumn() && end.getColumn() <= 8)) {
            return false;
        }

        ChessMove move = new ChessMove(start, end, null);
        ChessPiece blockingPiece = board.getPiece(end);

        //empty square
        if (blockingPiece == null) {
            moves.add(move);
            return true;
        }
        //enemy piece
        else if (blockingPiece.getTeamColor() != startPiece.getTeamColor()) {
            moves.add(move);
        }
        return false;
    }

    default ArrayList<ChessMove> lineMoves(ChessBoard board, ChessPosition start) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessPosition end;

        //top
        for(int i = 1; i < 8; i++) {
            end = new ChessPosition(start.getRow()+i, start.getColumn());
            if (!AddValidMove(board, start, end, moves)) {
                break;
            }
        }
        //right
        for(int i = 1; i < 8; i++) {
            end = new ChessPosition(start.getRow(), start.getColumn()+i);
            if (!AddValidMove(board, start, end, moves)) {
                break;
            }
        }
        //bot
        for(int i = 1; i < 8; i++) {
            end = new ChessPosition(start.getRow()-i, start.getColumn());
            if (!AddValidMove(board, start, end, moves)) {
                break;
            }        }
        //left
        for(int i = 1; i < 8; i++) {
            end = new ChessPosition(start.getRow(), start.getColumn()-i);
            if (!AddValidMove(board, start, end, moves)) {
                break;
            }
        }

        return moves;
    }

    default ArrayList<ChessMove> diagonalMoves(ChessBoard board, ChessPosition start) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessPosition end;

        //topleft
        for(int i = 1; i < 8; i++) {
            end = new ChessPosition(start.getRow()+i, start.getColumn()-i);
            if (!AddValidMove(board, start, end, moves)) {
                break;
            }
        }
        //topright
        for(int i = 1; i < 8; i++) {
            end = new ChessPosition(start.getRow()+i, start.getColumn()+i);
            if (!AddValidMove(board, start, end, moves)) {
                break;
            }
        }
        //botleft
        for(int i = 1; i < 8; i++) {
            end = new ChessPosition(start.getRow()-i, start.getColumn()-i);
            if (!AddValidMove(board, start, end, moves)) {
                break;
            }        }
        //botright
        for(int i = 1; i < 8; i++) {
            end = new ChessPosition(start.getRow()-i, start.getColumn()+i);
            if (!AddValidMove(board, start, end, moves)) {
                break;
            }
        }

        return moves;
    }
}
