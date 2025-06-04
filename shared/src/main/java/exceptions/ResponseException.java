package exceptions;

public class ResponseException extends RuntimeException {
  String message;
  int status;

  public ResponseException(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public int getStatus() {
    return status;
  }
}
