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
}
