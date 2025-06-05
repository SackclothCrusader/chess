import chess.*;
import client.Repl;

public class Main {
    private static final String URL = "http://localhost:8080";

    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        Repl repl = new Repl(URL);
        repl.run();
    }
}