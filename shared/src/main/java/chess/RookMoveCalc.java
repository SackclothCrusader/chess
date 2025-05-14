package chess;

import java.util.ArrayList;

public class RookMoveCalc implements MoveCalc{
    @Override
    public ArrayList<ChessMove> moveCalc(ChessBoard board, ChessPosition start) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessPosition end;

        //top
        for(int i = 1; i < 8; i++) {
            end = new ChessPosition(start.getRow()+i, start.getColumn());
            if (!AddValidMove(board, start, end, moves)) {
                break;
            }
        }
        //right
        for(int i = 1; i < 8; i++) {
            end = new ChessPosition(start.getRow(), start.getColumn()+i);
            if (!AddValidMove(board, start, end, moves)) {
                break;
            }
        }
        //bot
        for(int i = 1; i < 8; i++) {
            end = new ChessPosition(start.getRow()-i, start.getColumn());
            if (!AddValidMove(board, start, end, moves)) {
                break;
            }        }
        //left
        for(int i = 1; i < 8; i++) {
            end = new ChessPosition(start.getRow(), start.getColumn()-i);
            if (!AddValidMove(board, start, end, moves)) {
                break;
            }
        }

        return moves;
    }
}
