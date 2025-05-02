package chess;

import java.util.ArrayList;
import java.util.Collection;

public interface PieceMovesCalculator {
    abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position);

    //returns true if the piece can move past the square
    default boolean addValidMove(ChessBoard board, ChessPosition position, ChessPosition endPos, ArrayList<ChessMove> validMoves) {
        ChessMove tmp;
        if (board.getPiece(endPos) == null) {
            tmp = new ChessMove(position, endPos, null);
            validMoves.add(tmp);
            return true;
        }
        else if (board.getPiece(endPos).getTeamColor() != board.getPiece(position).getTeamColor()) {
            tmp = new ChessMove(position, endPos, null);
            validMoves.add(tmp);
        }
        return false;
    }
}