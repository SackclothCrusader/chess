package chess;

import java.util.ArrayList;
import java.util.Collection;

public interface PieceMovesCalculator {
    abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position);
}