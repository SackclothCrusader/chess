import chess.*;
import spark.Spark;

public class Main {
    public static void main(String[] args) {
//        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
//        System.out.println("♕ 240 Chess Server: " + piece);

        Spark.port(8080);
        Spark.staticFiles.location("web");


        Spark.get("/hello/:name", (req, res) -> { return "Hello " + req.params(":name");});
    }
}