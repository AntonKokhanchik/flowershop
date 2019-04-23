package flowershop.backend.exception;

public class UserValidationException extends Exception {
    public static final String WRONG_LOGIN = "This login does not exist";
    public static final String WRONG_PASSWORD = "Wrong password";

    public UserValidationException(String message) {
        super(message);
    }
}
