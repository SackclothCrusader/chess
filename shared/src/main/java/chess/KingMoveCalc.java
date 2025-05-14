package chess;

import java.util.ArrayList;

public class KingMoveCalc implements MoveCalc{
    @Override
    public ArrayList<ChessMove> moveCalc(ChessBoard board, ChessPosition start) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        //topleft
        ChessPosition end = new ChessPosition(start.getRow()+1, start.getColumn()-1);
        AddValidMove(board, start, end, moves);
        //top
        end = new ChessPosition(start.getRow()+1, start.getColumn());
        AddValidMove(board, start, end, moves);
        //topright
        end = new ChessPosition(start.getRow()+1, start.getColumn()+1);
        AddValidMove(board, start, end, moves);
        //left
        end = new ChessPosition(start.getRow(), start.getColumn()-1);
        AddValidMove(board, start, end, moves);
        //right
        end = new ChessPosition(start.getRow(), start.getColumn()+1);
        AddValidMove(board, start, end, moves);
        //botleft
        end = new ChessPosition(start.getRow()-1, start.getColumn()-1);
        AddValidMove(board, start, end, moves);
        //bot
        end = new ChessPosition(start.getRow()-1, start.getColumn());
        AddValidMove(board, start, end, moves);
        //botright
        end = new ChessPosition(start.getRow()-1, start.getColumn()+1);
        AddValidMove(board, start, end, moves);
        return moves;
    }
}
