package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();
        ChessPosition endPos;

        //top
        endPos = new ChessPosition(position.getRow()+1, position.getColumn());
        addValidMove(board, position, endPos, validMoves);
        //bot
        endPos = new ChessPosition(position.getRow()-1, position.getColumn());
        addValidMove(board, position, endPos, validMoves);
        //left
        endPos = new ChessPosition(position.getRow(), position.getColumn()-1);
        addValidMove(board, position, endPos, validMoves);
        //right
        endPos = new ChessPosition(position.getRow(), position.getColumn()+1);
        addValidMove(board, position, endPos, validMoves);

        //top left
        endPos = new ChessPosition(position.getRow()+1, position.getColumn()-1);
        addValidMove(board, position, endPos, validMoves);
        //bot left
        endPos = new ChessPosition(position.getRow()-1, position.getColumn()-1);
        addValidMove(board, position, endPos, validMoves);
        //bottom right
        endPos = new ChessPosition(position.getRow()-1, position.getColumn()+1);
        addValidMove(board, position, endPos, validMoves);
        //top right
        endPos = new ChessPosition(position.getRow()+1, position.getColumn()+1);
        addValidMove(board, position, endPos, validMoves);

        return validMoves;
    }
}
