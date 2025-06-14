package chess;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor teamToPlay;
    ChessBoard gameBoard;
    TeamColor resign;
    ArrayList<ChessMove> moveHistory;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return teamToPlay == chessGame.teamToPlay && Objects.equals(gameBoard, chessGame.gameBoard)
                && Objects.equals(moveHistory, chessGame.moveHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamToPlay, gameBoard, moveHistory);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "teamToPlay=" + teamToPlay +
                ", gameBoard=" + gameBoard +
                ", moveHistory=" + moveHistory +
                '}';
    }

    public ChessGame() {
        setTeamTurn(TeamColor.WHITE);
        setBoard(new ChessBoard());
        gameBoard.resetBoard();
        moveHistory = new ArrayList<>();
    }


    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK;
    }

    private ChessPosition getKing(ChessBoard board, TeamColor teamColor) { //:(
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                ChessPiece p = board.getPiece(pos);
                if (p != null && p.getPieceType() == ChessPiece.PieceType.KING && p.getTeamColor() == teamColor) {
                    return pos;
                }
            }
        }
        return null; // Shouldn't happen unless king is captured
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) { //:(
        if (resign != null) {
            return new ArrayList<>();
        }

        ChessPiece piece = new ChessPiece(gameBoard.getPiece(startPosition).getTeamColor(), gameBoard.getPiece(startPosition).getPieceType());
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>(piece.pieceMoves(gameBoard, startPosition));

        for(int i = 0; i < validMoves.size(); i++) {
            //make copy of board
            ChessBoard test = new ChessBoard();
            for (int j = 1; j <= 8; j++) {
                for (int k = 1; k <= 8; k++) {
                    ChessPosition tmp = new ChessPosition(j, k);
                    ChessPiece copyPiece = gameBoard.getPiece(tmp);
                    if (copyPiece != null) {
                        test.addPiece(tmp, copyPiece);
                    }
                }
            }

            //test the move
            test.movePiece(validMoves.get(i));
            if (testCheck(test, piece.getTeamColor())) {
                validMoves.remove(i);
                i--;
            }
        }

        return validMoves;
    }


    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //piece exists
        if(gameBoard.getPiece(move.getStartPosition()) == null) {
            throw new InvalidMoveException();
        }
        //correct team plays
        if(gameBoard.getPiece(move.getStartPosition()).getTeamColor() != teamToPlay) {
            throw new InvalidMoveException();
        }

        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        for(ChessMove i : validMoves) {
            if (i.equals(move)) {
                gameBoard.movePiece(move);
                moveHistory.add(move);
                if (teamToPlay == TeamColor.WHITE) {
                    setTeamTurn(TeamColor.BLACK);
                }
                else if (teamToPlay == TeamColor.BLACK) {
                    setTeamTurn(TeamColor.WHITE);
                }
                return;
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
    public boolean isInCheck(TeamColor teamColor) { //:(
        return testCheck(gameBoard, teamColor);
    }

    private boolean testCheck(ChessBoard board, TeamColor teamColor) {
        TeamColor opponent = (teamColor == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
        ChessPosition kingPos = getKing(board, teamColor);

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(pos);
                if (checker(opponent, kingPos, pos, piece, board) == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private int checker(TeamColor opponent, ChessPosition kingPos, ChessPosition pos, ChessPiece piece, ChessBoard board) {
        if (piece != null && piece.getTeamColor() == opponent) {
            Collection<ChessMove> moves = piece.pieceMoves(board, pos);
            for (ChessMove move : moves) {
                if (move.getEndPosition().equals(kingPos)) {
                    return 1;
                }
            }
        }
        return 0;
    }

    private boolean validMoves(TeamColor color) {
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece piece = gameBoard.getPiece(pos);

                if (piece != null && piece.getTeamColor() == color) {
                    Collection<ChessMove> legalMoves = validMoves(pos);
                    if (!legalMoves.isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) { //:(
        if (!isInCheck(teamColor)) {
            return false;
        }

        return validMoves(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) { //:(
        if (isInCheck(teamColor)){
            return false;
        }

        return validMoves(teamColor);
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

    public void resign(TeamColor team) {
        resign = team;
    }
}