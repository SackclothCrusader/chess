package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();
        ChessPosition endPos;

        //right
        for (int i = 1; position.getColumn() + i <= 8; i++) {
            endPos = new ChessPosition(position.getRow(), position.getColumn()+i);
            if (!addValidMove(board, position, endPos, validMoves)) {
                break;
            }
        }
        //left
        for (int i = 1;position.getColumn() - i >= 1; i++) {
            endPos = new ChessPosition(position.getRow(), position.getColumn()-i);
            if (!addValidMove(board, position, endPos, validMoves)) {
                break;
            }
        }
        //down
        for (int i = 1; position.getRow() - i >= 1; i++) {
            endPos = new ChessPosition(position.getRow()-i, position.getColumn());
            if (!addValidMove(board, position, endPos, validMoves)) {
                break;
            }
        }
        //up
        for (int i = 1; position.getRow() + i <= 8; i++) {
            endPos = new ChessPosition(position.getRow()+i, position.getColumn());
            if (!addValidMove(board, position, endPos, validMoves)) {
                break;
            }
        }

        return validMoves;
    }
}
