package chess;

import java.lang.reflect.Array;
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
    ArrayList<ChessMove> moveHistory;
    ChessPosition whiteKing;
    ChessPosition blackKing;
    ArrayList<ChessMove> whiteValid;
    ArrayList<ChessMove> blackValid;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return teamToPlay == chessGame.teamToPlay && Objects.equals(gameBoard, chessGame.gameBoard) && Objects.equals(moveHistory, chessGame.moveHistory) && Objects.equals(whiteKing, chessGame.whiteKing) && Objects.equals(blackKing, chessGame.blackKing) && Objects.equals(whiteValid, chessGame.whiteValid) && Objects.equals(blackValid, chessGame.blackValid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamToPlay, gameBoard, moveHistory, whiteKing, blackKing, whiteValid, blackValid);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "teamToPlay=" + teamToPlay +
                ", gameBoard=" + gameBoard +
                ", moveHistory=" + moveHistory +
                ", whiteKing=" + whiteKing +
                ", blackKing=" + blackKing +
                '}';
    }

    public ChessGame() {
        setTeamTurn(TeamColor.WHITE);
        setBoard(new ChessBoard());
        gameBoard.resetBoard();
        moveHistory = new ArrayList<>();
        whiteKing = new ChessPosition(1, 5);
        blackKing = new ChessPosition(8, 5);
        whiteValid = new ArrayList<>();
        blackValid = new ArrayList<>();
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
        BLACK;
    }


    private ChessPosition getKing(ChessBoard board, TeamColor teamColor) {
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

    private boolean testCheck(ChessBoard board, TeamColor teamColor) {
        if (teamColor == TeamColor.WHITE) {
            for(int i = 1; i <= 8; i++) {
                for(int j = 1; j <= 8; j++) {
                    ChessPosition tmp = new ChessPosition(i, j);
                    ChessPiece enemy = board.getPiece(tmp);
                    if (enemy != null && enemy.getTeamColor() == TeamColor.BLACK) {
                        for (ChessMove move : enemy.pieceMoves(board, tmp)) {
                            if (move.getEndPosition().equals(getKing(board, teamColor))) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        else {
            for(int i = 1; i <= 8; i++) {
                for(int j = 1; j <= 8; j++) {
                    ChessPosition tmp = new ChessPosition(i, j);
                    ChessPiece enemy = board.getPiece(tmp);
                    if (enemy != null && enemy.getTeamColor() == TeamColor.WHITE) {
                        for (ChessMove move : enemy.pieceMoves(board, tmp)) {
                            if (move.getEndPosition().equals(getKing(board, teamColor))) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
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

    // make list of all valid moves for teamColor
    private void validList(TeamColor teamColor) {
        if (teamColor == TeamColor.WHITE) {
            //clear list
            whiteValid.clear();
            //calculate black threatened squares
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPosition start = new ChessPosition(i, j);
                    ChessPiece piece = gameBoard.getPiece(start);
                    //ensure pieces are white
                    if (piece != null && piece.getTeamColor() == TeamColor.WHITE) {
                        ArrayList<ChessMove> addMe = new ArrayList<>(validMoves(start));
                        whiteValid.addAll(addMe);
                    }
                }
            }
        }
        else {
            //clear list
            blackValid.clear();
            //calculate black threatened squares
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPosition start = new ChessPosition(i, j);
                    ChessPiece piece = gameBoard.getPiece(start);
                    //ensure pieces are white
                    if (piece != null && piece.getTeamColor() == TeamColor.BLACK) {
                        ArrayList<ChessMove> addMe = new ArrayList<>(validMoves(start));
                        blackValid.addAll(addMe);
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
                    if (move.getStartPosition().equals(whiteKing)) {
                        whiteKing = move.getEndPosition();
                    }
                    setTeamTurn(TeamColor.BLACK);
                }
                else if (teamToPlay == TeamColor.BLACK) {
                    if (move.getStartPosition().equals(blackKing)) {
                        blackKing = move.getEndPosition();
                    }
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
    public boolean isInCheck(TeamColor teamColor) {
        return testCheck(gameBoard, teamColor);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if(isInCheck(teamColor)) {
            validList(teamColor);
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
        validList(teamColor);
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