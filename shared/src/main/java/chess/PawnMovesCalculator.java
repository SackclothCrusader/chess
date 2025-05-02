package chess;

import java.util.Collection;
import java.util.ArrayList;

public class PawnMovesCalculator implements PieceMovesCalculator{
    @Override
    public boolean addValidMove(ChessBoard board, ChessPosition position, ChessPosition endPos, ArrayList<ChessMove> validMoves) {
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
        if (board.getPiece(endPos).getTeamColor() != board.getPiece(position).getTeamColor()) {
            tmp = new ChessMove(position, endPos, null);
            validMoves.add(tmp);
        }
        return false;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();
        ChessMove endPos;
        final int WHITE_START_ROW = 2;
        final int BLACK_START_ROW = 7;

        //for white
        if (position.getRow())

        return validMoves;
    }
}
