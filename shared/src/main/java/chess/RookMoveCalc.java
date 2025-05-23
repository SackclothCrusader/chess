package chess;

import java.util.ArrayList;

public class RookMoveCalc implements MoveCalc{
    @Override
    public ArrayList<ChessMove> moveCalc(ChessBoard board, ChessPosition start) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        moves.addAll(lineMoves(board, start));

        return moves;
    }
}
