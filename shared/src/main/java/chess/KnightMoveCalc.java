package chess;

import java.util.ArrayList;

public class KnightMoveCalc implements MoveCalc{
    @Override
    public ArrayList<ChessMove> moveCalc(ChessBoard board, ChessPosition start) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessPosition end;

        //topleft
        end = new ChessPosition(start.getRow()+2, start.getColumn()-1);
        AddValidMove(board, start, end, moves);
        //topright
        end = new ChessPosition(start.getRow()+2, start.getColumn()+1);
        AddValidMove(board, start, end, moves);
        //botleft
        end = new ChessPosition(start.getRow()-2, start.getColumn()-1);
        AddValidMove(board, start, end, moves);
        //botright
        end = new ChessPosition(start.getRow()-2, start.getColumn()+1);
        AddValidMove(board, start, end, moves);
        //leftup
        end = new ChessPosition(start.getRow()+1, start.getColumn()-2);
        AddValidMove(board, start, end, moves);
        //leftdown
        end = new ChessPosition(start.getRow()-1, start.getColumn()-2);
        AddValidMove(board, start, end, moves);
        //rightup
        end = new ChessPosition(start.getRow()+1, start.getColumn()+2);
        AddValidMove(board, start, end, moves);
        //rightdown
        end = new ChessPosition(start.getRow()-1, start.getColumn()+2);
        AddValidMove(board, start, end, moves);

        return moves;
    }
}
