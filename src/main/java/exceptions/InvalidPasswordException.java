package exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("La contrassenya utilizada és massa fàcil. Ha de tenir almenys 8 caràcters, majuscules, digits i caracters especials.");
    }
}
