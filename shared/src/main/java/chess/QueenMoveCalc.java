package chess;

import java.util.ArrayList;

public class QueenMoveCalc implements MoveCalc{
    public ArrayList<ChessMove> moveCalc(ChessBoard board, ChessPosition start){
        ArrayList<ChessMove> moves = new ArrayList<>();

        moves.addAll(lineMoves(board, start));
        moves.addAll(diagonalMoves(board, start));

        return moves;
    }
}
