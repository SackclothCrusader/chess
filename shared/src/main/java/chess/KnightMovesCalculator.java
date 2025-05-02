package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KnightMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();
        ChessPosition endPos;

        // up, left
        endPos = new ChessPosition(position.getRow()-1, position.getColumn()+2);
        addValidMove(board, position, endPos, validMoves);
        //up, right
        endPos = new ChessPosition(position.getRow()+1, position.getColumn()+2);
        addValidMove(board, position, endPos, validMoves);
        //down, left
        endPos = new ChessPosition(position.getRow()-1, position.getColumn()-2);
        addValidMove(board, position, endPos, validMoves);
        //down, right
        endPos = new ChessPosition(position.getRow()+1, position.getColumn()-2);
        addValidMove(board, position, endPos, validMoves);
        //left, up
        endPos = new ChessPosition(position.getRow()-2, position.getColumn()+1);
        addValidMove(board, position, endPos, validMoves);
        //left, down
        endPos = new ChessPosition(position.getRow()-2, position.getColumn()+1);
        addValidMove(board, position, endPos, validMoves);
        //right, up
        endPos = new ChessPosition(position.getRow()+2, position.getColumn()+1);
        addValidMove(board, position, endPos, validMoves);
        //right, down
        endPos = new ChessPosition(position.getRow()+2, position.getColumn()-1);
        addValidMove(board, position, endPos, validMoves);

        return validMoves;
    }
}
