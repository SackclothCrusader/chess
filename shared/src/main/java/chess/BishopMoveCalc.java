package chess;

import java.util.ArrayList;

public class BishopMoveCalc implements MoveCalc{
    @Override
    public ArrayList<ChessMove> moveCalc(ChessBoard board, ChessPosition start) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        moves.addAll(diagonalMoves(board, start));

        return moves;
    }
}