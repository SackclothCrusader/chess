package chess;

import java.util.Collection;
import java.util.ArrayList;

public class PawnMovesCalculator implements PieceMovesCalculator{
    final int WHITE_START_ROW = 2;
    final int BLACK_START_ROW = 7;
    final int WHITE_PROMOTION_ROW = 8;
    final int BLACK_PROMOTION_ROW = 1;
    final int LEFT_EDGE = 1;
    final int RIGHT_EDGE = 8;

    private void promotion (ArrayList<ChessMove> validMoves) {
        for (int i = 0; i < validMoves.size(); i++ ) {
            ChessPosition endPos = validMoves.get(i).getEndPosition();
            ChessPosition startPos = validMoves.get(i).getStartPosition();
            if (endPos.getRow() == WHITE_PROMOTION_ROW || endPos.getRow() == BLACK_PROMOTION_ROW) {
                ChessMove load = new ChessMove(startPos, endPos, ChessPiece.PieceType.QUEEN);
                validMoves.add(i+1, load);
                load = new ChessMove(startPos, endPos, ChessPiece.PieceType.KNIGHT);
                validMoves.add(i+1, load);
                load = new ChessMove(startPos, endPos, ChessPiece.PieceType.BISHOP);
                validMoves.add(i+1, load);
                load = new ChessMove(startPos, endPos, ChessPiece.PieceType.ROOK);
                validMoves.add(i+1, load);
                validMoves.remove(i);
                i += 3;
            }
        }
    }

    @Override
    public boolean addValidMove(ChessBoard board, ChessPosition position, ChessPosition endPos, ArrayList<ChessMove> validMoves) {
        ChessMove tmp;

        //make sure endPosition is on the board first
        if (!(1 <= endPos.getRow() && endPos.getRow() <= 8 && 1 <= endPos.getColumn() && endPos.getColumn() <= 8)) {
            return false;
        }

        //empty square
        if (board.getPiece(endPos) == null) {
            tmp = new ChessMove(position, endPos, null);
            validMoves.add(tmp);
            return true;
        }
        //capture enemy
        else if (endPos.getColumn() != position.getColumn() && board.getPiece(endPos).getTeamColor() != board.getPiece(position).getTeamColor()) {
            tmp = new ChessMove(position, endPos, null);
            validMoves.add(tmp);
        }
        return false;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();
        ChessPosition endPos;
        ChessPiece pawn = board.getPiece(position);

        //for white
        if (pawn.getTeamColor() == ChessGame.TeamColor.WHITE) {
            endPos = new ChessPosition(position.getRow()+1, position.getColumn());
            if (addValidMove(board, position, endPos, validMoves) && position.getRow() == WHITE_START_ROW) {
                endPos = new ChessPosition(position.getRow()+2, position.getColumn());
                addValidMove(board, position, endPos, validMoves);
            }

            ChessPiece enemy;
            //capture left
            if (position.getColumn() != LEFT_EDGE) {
                endPos = new ChessPosition(position.getRow()+1, position.getColumn()-1);
                enemy = board.getPiece(endPos);
                if (enemy != null) {
                    addValidMove(board, position, endPos, validMoves);
                }
            }
            //capture right
            if (position.getColumn() != RIGHT_EDGE) {
                endPos = new ChessPosition(position.getRow()+1, position.getColumn()+1);
                enemy = board.getPiece(endPos);
                if (enemy != null) {
                    addValidMove(board, position, endPos, validMoves);
                }
            }
        }
        //for black
        else if (pawn.getTeamColor() == ChessGame.TeamColor.BLACK) {
            endPos = new ChessPosition(position.getRow() - 1, position.getColumn());
            if (addValidMove(board, position, endPos, validMoves) && position.getRow() == BLACK_START_ROW) {
                endPos = new ChessPosition(position.getRow() - 2, position.getColumn());
                addValidMove(board, position, endPos, validMoves);
            }

            ChessPiece enemy;
            //capture left
            if (position.getColumn() != LEFT_EDGE) {
                endPos = new ChessPosition(position.getRow() - 1, position.getColumn() - 1);
                enemy = board.getPiece(endPos);
                if (enemy != null) {
                    addValidMove(board, position, endPos, validMoves);
                }
            }
            //capture right
            if (position.getColumn() != RIGHT_EDGE) {
                endPos = new ChessPosition(position.getRow() - 1, position.getColumn() + 1);
                enemy = board.getPiece(endPos);
                if (enemy != null) {
                    addValidMove(board, position, endPos, validMoves);
                }
            }
        }

        promotion(validMoves);
        return validMoves;
    }
}
