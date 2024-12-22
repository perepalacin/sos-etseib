package exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super(
                "Per poder acceidr a aquest recurs necessites estar registrat a SOS - ETSEIB"
        );
    }
}
