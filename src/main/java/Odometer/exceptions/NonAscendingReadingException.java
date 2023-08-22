package Odometer.exceptions;

public class NonAscendingReadingException extends Exception{
    private static final long serialVersionUID = 1L;

	public NonAscendingReadingException(String message) {
        super(message);
    }
}
