package exceptions;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super("Invalid email provided, please use your student email.");
    }
}
