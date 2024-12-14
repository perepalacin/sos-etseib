package exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Invalid Password, it should have at least 8 characters, one capital letter, numbers and a special character.");
    }
}
