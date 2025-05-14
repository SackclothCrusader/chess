package chess;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PawnMoveCalc implements MoveCalc{
    private final int WHITE_START_ROW = 2;
    private final int BLACK_START_ROW = 7;
    private final int WHITE_PROMOTE = 8;
    private final int BLACK_PROMOTE = 1;
    private final int LEFT_EDGE = 1;
    private final int RIGHT_EDGE = 8;

    private void promote(ArrayList<ChessMove> moves){
        //based on the logic pawns will not be able to move backwards
        for (int i = 0; i < moves.size(); i++) {
            if (moves.get(i).getEndPosition().getRow() == WHITE_PROMOTE || moves.get(i).getEndPosition().getRow() == BLACK_PROMOTE){
                //add promotions
                ChessMove move = new ChessMove(moves.get(i).getStartPosition(), moves.get(i).getEndPosition(), ChessPiece.PieceType.QUEEN);
                moves.add(i+1, move);
                move = new ChessMove(moves.get(i).getStartPosition(), moves.get(i).getEndPosition(), ChessPiece.PieceType.KNIGHT);
                moves.add(i+1, move);
                move = new ChessMove(moves.get(i).getStartPosition(), moves.get(i).getEndPosition(), ChessPiece.PieceType.ROOK);
                moves.add(i+1, move);
                move = new ChessMove(moves.get(i).getStartPosition(), moves.get(i).getEndPosition(), ChessPiece.PieceType.BISHOP);
                moves.add(i+1, move);

                //cleanup
                moves.remove(i);
                i += 3;
            }
        }
    }

    private void AddCaptureMoves(ChessBoard board, ChessPosition start, ArrayList<ChessMove> moves){
        ChessPiece pawn = board.getPiece(start);
        ChessPiece enemy;
        ChessPosition end;

        //white
        if (pawn.getTeamColor() == ChessGame.TeamColor.WHITE) {
            //left
            end = new ChessPosition(start.getRow()+1, start.getColumn()-1);
            if (LEFT_EDGE <= end.getColumn()) {
                enemy = board.getPiece(end);
                if (enemy != null) {
                    AddValidMove(board, start, end, moves);
                }
            }
            //right
            end = new ChessPosition(start.getRow()+1, start.getColumn()+1);
            if (RIGHT_EDGE >= end.getColumn()) {
                enemy = board.getPiece(end);
                if (enemy != null) {
                    AddValidMove(board, start, end, moves);
                }
            }
        }
        else {
            //left
            end = new ChessPosition(start.getRow()-1, start.getColumn()-1);
            if (LEFT_EDGE <= end.getColumn()) {
                enemy = board.getPiece(end);
                if (enemy != null) {
                    AddValidMove(board, start, end, moves);
                }
            }
            //right
            end = new ChessPosition(start.getRow()-1, start.getColumn()+1);
            if (RIGHT_EDGE >= end.getColumn()) {
                enemy = board.getPiece(end);
                if (enemy != null) {
                    AddValidMove(board, start, end, moves);
                }
            }
        }
    }

    @Override
    public ArrayList<ChessMove> moveCalc(ChessBoard board, ChessPosition start) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessPosition end;
        ChessPiece pawn = board.getPiece(start);

        //white
        if (pawn.getTeamColor() == ChessGame.TeamColor.WHITE) {
            end = new ChessPosition(start.getRow()+1, start.getColumn());
            if (board.getPiece(end) == null && AddValidMove(board, start, end, moves) && start.getRow() == WHITE_START_ROW) {
                end = new ChessPosition(start.getRow() + 2, start.getColumn());
                if (board.getPiece(end) == null) {
                    AddValidMove(board, start, end, moves);
                }
            }
        }
        //black
        else {
            end = new ChessPosition(start.getRow()-1, start.getColumn());
            if (board.getPiece(end) == null && AddValidMove(board, start, end, moves) && start.getRow() == BLACK_START_ROW) {
                end = new ChessPosition(start.getRow() - 2, start.getColumn());
                if (board.getPiece(end) == null) {
                    AddValidMove(board, start, end, moves);
                }
            }
        }

        AddCaptureMoves(board, start, moves);
        promote(moves);
        return moves;
    }
}
