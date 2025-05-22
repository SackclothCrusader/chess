package dataaccess;

public class BadRequestException extends Exception {
    String message;

    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }
}
