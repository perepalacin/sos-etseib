package exceptions;

public class InvalidValidationTokenException extends RuntimeException {
    public InvalidValidationTokenException(String message) {
        super(message);
    }
}
