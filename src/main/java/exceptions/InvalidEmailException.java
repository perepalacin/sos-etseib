package exceptions;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super("Adreça electrònica utilizada invalida. Si us plau, utilizta la teva adreça UPC.");
    }
}
