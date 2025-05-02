package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        ChessPosition endPos;
        ChessMove tmp;

        //up right diagonal
        for (int i = 1; position.getRow() + i <= 8 && position.getColumn() + i <= 8 ; i++) {
            endPos = new ChessPosition(position.getRow()+i, position.getColumn()+i);
            tmp = new ChessMove(position, endPos, null);
            validMoves.add(tmp);
        }
        //up left diagonal
        for (int i = 1; position.getRow() - i >= 1 && position.getColumn() + i <= 8 ; i++) {
            endPos = new ChessPosition(position.getRow()-i, position.getColumn()+i);
            tmp = new ChessMove(position, endPos, null);
            validMoves.add(tmp);
        }
        //down left diagonal
        for (int i = 1; position.getRow() - i >= 1 && position.getColumn() - i >= 1 ; i++) {
            endPos = new ChessPosition(position.getRow()-i, position.getColumn()-i);
            tmp = new ChessMove(position, endPos, null);
            validMoves.add(tmp);
        }
        //down right diagonal
        for (int i = 1; position.getRow() + i <= 8 && position.getColumn() - i >= 1 ; i++) {
            endPos = new ChessPosition(position.getRow()+i, position.getColumn()-i);
            tmp = new ChessMove(position, endPos, null);
            validMoves.add(tmp);
        }

        return validMoves;
    }
}
