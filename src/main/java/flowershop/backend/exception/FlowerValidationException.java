package flowershop.backend.exception;

public class FlowerValidationException extends Exception {
    public static final String NEGATIVE_COUNT = "Count cannot be negative";
    public static final String NOT_ENOUGH_FLOWERS = "Ran out of flowers, fix your order";
    public static final String NEGATIVE_PRICE = "Price cannot be negative";
    public static final String WRONG_PRICE = "Price must be a decimal number";
    public static final String WRONG_COUNT = "Count must be an integer number";

    public FlowerValidationException(String message) {
        super(message);
    }
}
