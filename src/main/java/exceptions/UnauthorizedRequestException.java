package exceptions;

public class UnauthorizedRequestException extends RuntimeException {
    public UnauthorizedRequestException() {
        super(
                "No tens els permissos necessaries per realitzar aquesta operació"
        );
    }
}
