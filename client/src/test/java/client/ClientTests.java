package client;

import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.api.Test;

public class ClientTests {
    @Test
    public void printTest() {
        GameClient client = new GameClient("http://localhost:8080");
        Repl.AUTH = "fd3dd15f-0a67-4242-8043-bd0ee80597c2";
        client.printBoard(1, ChessGame.TeamColor.WHITE);
        client.printBoard(1, ChessGame.TeamColor.BLACK);
    }
}
