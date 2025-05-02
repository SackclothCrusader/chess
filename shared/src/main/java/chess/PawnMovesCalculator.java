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
        ChessPosition endPos;
        ChessPiece pawn = board.getPiece(position);
        final int WHITE_START_ROW = 2;
        final int BLACK_START_ROW = 7;
        final int WHITE_PROMOTION_ROW = 8;
        final int BLACK_PROMOTION_ROW = 1;

        //for white
        if (pawn.getTeamColor() == ChessGame.TeamColor.WHITE) {
            //first move (for loop works better for if it's blocked)
            if (position.getRow() == WHITE_START_ROW) {
                for (int i = 1; i <= 2; i++) {
                    endPos = new ChessPosition(position.getRow()+i, position.getColumn());
                    if (!addValidMove(board, position, endPos, validMoves)) {
                        break;
                    }
                }
            }
            //not first move
            else {
                endPos = new ChessPosition(position.getRow()+1, position.getColumn());
                addValidMove(board, position, endPos, validMoves);
            }

            //capture left
            endPos = new ChessPosition(position.getRow()+1, position.getColumn()-1);
            if (board.getPiece(endPos).getTeamColor() == ChessGame.TeamColor.BLACK) {
                addValidMove(board, position, endPos, validMoves);
            }

            //capture right
            endPos = new ChessPosition(position.getRow()+1, position.getColumn()+1);
            if (board.getPiece(endPos).getTeamColor() == ChessGame.TeamColor.BLACK) {
                addValidMove(board, position, endPos, validMoves);
            }

            //promotion

        }

        return validMoves;
    }
}
