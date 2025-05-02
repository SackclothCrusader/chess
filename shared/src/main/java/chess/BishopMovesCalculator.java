package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        ChessPosition endPos;

        //up right diagonal
        for (int i = 1; position.getRow() + i <= 8 && position.getColumn() + i <= 8 ; i++) {
            endPos = new ChessPosition(position.getRow()+i, position.getColumn()+i);
            if (!addValidMove(board, position, endPos, validMoves)) {
                break;
            }
        }
        //up left diagonal
        for (int i = 1; position.getRow() - i >= 1 && position.getColumn() + i <= 8 ; i++) {
            endPos = new ChessPosition(position.getRow()-i, position.getColumn()+i);
            if (!addValidMove(board, position, endPos, validMoves)) {
                break;
            }
        }
        //down left diagonal
        for (int i = 1; position.getRow() - i >= 1 && position.getColumn() - i >= 1 ; i++) {
            endPos = new ChessPosition(position.getRow()-i, position.getColumn()-i);
            if (!addValidMove(board, position, endPos, validMoves)) {
                break;
            }
        }
        //down right diagonal
        for (int i = 1; position.getRow() + i <= 8 && position.getColumn() - i >= 1 ; i++) {
            endPos = new ChessPosition(position.getRow()+i, position.getColumn()-i);
            if (!addValidMove(board, position, endPos, validMoves)) {
                break;
            }
        }

        return validMoves;
    }
}
