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
        try {
            return switch (cmd) {
                case "register" -> register(tokens[1], tokens[2], tokens[3]);
                case "login" -> login(tokens[1], tokens[2]);
                case "quit" -> quit();
                default -> help();
            };
        } catch (Exception e) {
            return help();
        }
    }

    public boolean help() {
        System.out.println("""
                Please register or log in to access the main service. To show this menu type help.
               
                Commands:
                - help
                    Display this page.
                - register <username> <password> <email>
                    Create a new user and login as that user.
                - login <username> <password>
                    Log in as an existing user.
                - quit
                    End the current session and close 240 Chess client.
               """);
        return false;
    }

    private boolean register(String user, String password, String email) {
        try {
            Repl.auth = facade.register(user, password, email);
            System.out.println("Your account has been created and you are logged in as " + user + ".");
        } catch (ResponseException e) {
            System.out.println("There was an internal error. Please try again.");
            return false;
        }
        return false;
    }

    private boolean login(String user, String password) {
        try {
            Repl.auth = facade.login(user, password);
            System.out.println("You are now logged in as " + user + ".");
        } catch (ResponseException e) {
            System.out.println("There was an internal error. Please try again.");
        }
        return false;
    }

    private boolean quit() {
        System.out.println("Goodbye :)");
        return true;
    }
}