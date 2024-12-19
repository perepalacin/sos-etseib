package exceptions;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException() {
        super(
            "Ja hi ha un altre usuari registrat amb aquest correu, si us plau, feu servir un altre."
        );
    }
}
