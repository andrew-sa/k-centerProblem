package places.exceptions;

/**
 * This exception occurs when the user tries to place a city (or center) over another city (or center).
 * @author Andrea Mogavero
 *
 */
public class DuplicatePlaceException extends Exception {

	public DuplicatePlaceException() {
		super("In this position a place is already present.");
	}

	public DuplicatePlaceException(String message) {
		super(message);
	}
	
}
