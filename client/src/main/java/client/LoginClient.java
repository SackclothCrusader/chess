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
                case "help" -> help();
                case "register" -> register(tokens[1], tokens[2], tokens[3]);
                case "login" -> login(tokens[1], tokens[2]);
                case "quit" -> quit();
                default -> {
                    System.out.println("Unknown command. Type help to open the help menu.");
                    yield false;
                }
            };
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Incorrect number of arguments for " + cmd + "\nType help to open the help menu.");
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect argument type for " + cmd + "\nType help to open the help menu.");
        } catch (Exception e) {
            System.out.println("There was an error with command " + cmd + "\nType help to open the help menu.");
        }
        return false;
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