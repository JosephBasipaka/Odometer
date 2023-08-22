package Odometer.exceptions;

public class ReadingSizeMismatchException extends Exception {
    private static final long serialVersionUID = 1L;

	public ReadingSizeMismatchException(String message) {
        super(message);
    }
}
