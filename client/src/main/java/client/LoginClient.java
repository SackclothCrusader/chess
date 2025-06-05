package client;

import exceptions.ResponseException;

public class LoginClient {
    private final ServerFacade facade;

    LoginClient(String url) {
        facade = new ServerFacade(url);
    }

    public boolean eval(String in) {
        var tokens = in.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "";
        return switch (cmd) {
            case "register" -> register(tokens[1], tokens[2], tokens[3]);
            case "login" -> login(tokens[1], tokens[2]);
            case "quit" -> quit();
            default -> help();
        };
    }

    public boolean help() {
        System.out.println("""
                Please register or log in to access the main service.
               
                Commands:
                - register <username> <password> <email>
                - login <username> <password>
                - quit
               """);
        return false;
    }

    private boolean register(String user, String password, String email) {
        try {
            Repl.AUTH = facade.register(user, password, email);
        } catch (ResponseException e) {
            System.out.println("There was an internal error. Please try again.");
            return false;
        }
        System.out.println("Your account has been created and you are logged in as " + user + ".");
        return true;
    }

    private boolean login(String user, String password) {
        try {
            Repl.AUTH = facade.login(user, password);
        } catch (ResponseException e) {
            System.out.println("There was an internal error. Please try again.");
            return false;
        }
        System.out.println("You are now logged in as " + user + ".");
        return true;
    }

    private boolean quit() {
        System.out.println("Goodbye :)");
        return true;
    }
}