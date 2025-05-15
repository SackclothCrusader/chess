package chess;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ArrayList;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor teamToPlay;
    ChessBoard gameBoard;
    ArrayList<ChessMove> moveHistory;
    ChessPosition whiteKing;
    ChessPosition blackKing;
    ArrayList<ChessPosition> whiteThreatens;
    ArrayList<ChessPosition> blackThreatens;
    ArrayList<ChessMove> whiteValid;
    ArrayList<ChessMove> blackValid;

    public ChessGame() {
        setTeamTurn(TeamColor.WHITE);
        setBoard(new ChessBoard());
        gameBoard.resetBoard();
        whiteKing = new ChessPosition(1, 5);
        blackKing = new ChessPosition(8, 5);
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamToPlay;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamToPlay = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece tmp =  new ChessPiece(gameBoard.getPiece(startPosition).getTeamColor(), gameBoard.getPiece(startPosition).getPieceType());

        Collection<ChessMove> validMoves = tmp.pieceMoves(gameBoard, startPosition);

        return validMoves;
    }

    // finds threatened squares
    private void threatenedSquares(TeamColor teamColor) {
        if (teamColor == TeamColor.WHITE) {
            //clear threatened squares list
            blackThreatens.clear();
            //calculate black threatened squares
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    ChessPosition start = new ChessPosition(i, j);
                    ChessPiece piece = gameBoard.getPiece(start);
                    //ensure pieces are black
                    if (piece != null && piece.getTeamColor() == TeamColor.BLACK) {
                        ArrayList<ChessMove> addMe = new ArrayList<>(piece.pieceMoves(getBoard(), start));

                        //special pawn logic
                        if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                            for (int k = 0; k < addMe.size(); k++) {
                                if (addMe.get(i).getEndPosition().getColumn() == start.getColumn()) {
                                    addMe.remove(i);
                                    k--;
                                }
                            }
                        }
                        //extract end positions and add to blackThreatens
                        ArrayList<ChessPosition> tmp = new ArrayList<>();
                        for (ChessMove move : addMe) {
                            tmp.add(move.getEndPosition());
                        }
                        blackThreatens.addAll(tmp);
                    }
                }
            }
        }
        else {
            //clear threatened squares list
            whiteThreatens.clear();
            //calculate white threatened squares
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    ChessPosition start = new ChessPosition(i, j);
                    ChessPiece piece = gameBoard.getPiece(start);
                    //ensure pieces are white
                    if (piece != null && piece.getTeamColor() == TeamColor.WHITE) {
                        ArrayList<ChessMove> addMe = new ArrayList<>(piece.pieceMoves(getBoard(), start));

                        //special pawn logic
                        if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                            for (int k = 0; k < addMe.size(); k++) {
                                if (addMe.get(i).getEndPosition().getColumn() == start.getColumn()) {
                                    addMe.remove(i);
                                    k--;
                                }
                            }
                        }
                        //extract end positions and add to whiteThreatens
                        ArrayList<ChessPosition> tmp = new ArrayList<>();
                        for (ChessMove move : addMe) {
                            tmp.add(move.getEndPosition());
                        }
                        whiteThreatens.addAll(tmp);
                    }
                }
            }
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        for(ChessMove i : validMoves) {
            if (i == move) {
                gameBoard.movePiece(move);
                moveHistory.add(move);
                if (teamToPlay == TeamColor.WHITE && move.getStartPosition() == whiteKing) {
                    whiteKing = move.getEndPosition();
                }
                else if (teamToPlay == TeamColor.BLACK && move.getStartPosition() == blackKing) {
                    blackKing = move.getEndPosition();
                }
                break;
            }
        }
        throw new InvalidMoveException();
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        threatenedSquares(teamColor);
        if (teamColor == TeamColor.WHITE) {
            //find king in threatened squares
            for (ChessPosition i : blackThreatens) {
                if (whiteKing == i) {
                    return true;
                }
            }
            return false;
        }
        else {
            //find king in threatened squares
            for (ChessPosition i : whiteThreatens) {
                if (blackKing == i) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if(isInCheck(teamColor)) {
            if (teamColor == TeamColor.WHITE && whiteValid.isEmpty()) {
                return true;
            }
            else if (teamColor == TeamColor.BLACK && blackValid.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (teamColor == TeamColor.WHITE && whiteValid.isEmpty()) {
            return true;
        }
        else if (teamColor == TeamColor.BLACK && blackValid.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        gameBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return gameBoard;
    }
}
