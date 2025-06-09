package client;

import chess.ChessGame;
import exceptions.ResponseException;
import model.GameData;
import java.util.Collection;

public class HomeClient {
    private ServerFacade facade;
    HomeClient(String url) {
        facade = new ServerFacade(url);
    }

    public boolean eval(String in) {
        var tokens = in.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "";
        if (cmd.equals("create") || cmd.equals("list") || cmd.equals("play") || cmd.equals("observe")) {
            cmd = tokens[0] + " " + tokens[1];
        }

        try {
            return switch (cmd) {
                case "logout" -> logout();
                case "create game" -> create(tokens[2]);
                case "list games" -> list();
                case "play game" -> join(Integer.parseInt(tokens[2]), tokens[3]);
                case "observe game" -> observe(Integer.parseInt(tokens[2]));
                case "quit" -> quit();
                default -> help();
            };
        } catch (Exception e) {
            return help();
        }
    }

    private boolean quit() {
        facade.logout(Repl.AUTH);
        System.out.println("Goodbye :)");
        return true;
    }

    private boolean logout(){
        try {
            facade.logout(Repl.AUTH);
            Repl.AUTH = "";
            System.out.println("You have been logged out.");
        } catch (ResponseException e) {
            System.out.println("There was an internal error. Please try again.");
        }
        return false;
    }

    private boolean create(String name){
        try {
            facade.createGame(Repl.AUTH, name);
            System.out.println("Game " + name + " has been created.");
        } catch (ResponseException e) {
            System.out.println("There was an internal error. Please try again.");
        }
        return false;
    }

    private boolean list(){
        Collection<GameData> games = null;
        try {
            games = facade.listGames(Repl.AUTH);
            System.out.println("Games:");
            for (GameData game : games) {
                System.out.println(game); // relies on your custom toString()
            }

        } catch (ResponseException e) {
            System.out.println("There was an internal error. Please try again.");
        }
        return false;
    }

    private boolean join(int gameID, String playerColor){
        ChessGame.TeamColor color;

        if (playerColor.equals("white") || playerColor.equals("w")) {
            color = ChessGame.TeamColor.WHITE;
        }
        else if (playerColor.equals("black") || playerColor.equals("b")) {
            color = ChessGame.TeamColor.BLACK;
        }
        else {
            System.out.println("Invalid color.");
            return false;
        }

        try {
            facade.joinGame(Repl.AUTH, color, gameID);
            Repl.GAME_ID = gameID;
            System.out.println("Game has been joined.");
        } catch (ResponseException e) {
            System.out.println("There was an internal error. Please try again.");
        }
        return false;
    }

    private boolean observe(int gameID){
        return false;
    }

    public boolean help() {
        System.out.println("""
                Welcome to the 240 Chess client! To show this menu type help.
               
                Commands:
                - help
                    Display this page.
                - logout
                    Logout current user and return to login.
                - create game <name>
                    Creates a new game. Note that you must join the game manually.
                - list games
                    Lists all games in the database.
                - play game <gameID> <BLACK/WHITE>
                    Allows the user to join a game as black or white and play.
                - observe game <gameID>
                    Spectate a game.
                - quit
                    End the current session and close 240 Chess client.
               """);
        return false;
    }
}