package chess;

import java.util.ArrayList;
import java.util.Collection;

public interface PieceMovesCalculator {
    abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position);

    //returns true if the piece can move past the square
    default boolean addValidMove(ChessBoard board, ChessPosition position, ChessPosition endPos, ArrayList<ChessMove> validMoves) {
        ChessMove tmp;

        //make sure endPosition is on the board first
        if (!(1 <= endPos.getRow() && endPos.getRow() <= 8 && 1 <= endPos.getColumn() && endPos.getColumn() <= 8)) {
            return false;
        }

        //empty square
        if (board.getPiece(endPos) == null) {
            tmp = new ChessMove(position, endPos, null);
            validMoves.add(tmp);
            return true;
        }
        //capture enemy
        else if (board.getPiece(endPos).getTeamColor() != board.getPiece(position).getTeamColor()) {
            tmp = new ChessMove(position, endPos, null);
            validMoves.add(tmp);
        }
        return false;
    }
}