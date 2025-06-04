package exceptions;

public class AlreadyTakenException extends Exception {
    String message;

    public AlreadyTakenException(String message) {
        super(message);
        this.message = message;
    }
}
