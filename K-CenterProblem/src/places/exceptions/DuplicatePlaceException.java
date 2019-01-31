package places.exceptions;

public class DuplicatePlaceException extends Exception {

	public DuplicatePlaceException() {
		super("In this position a place is already present.");
	}

	public DuplicatePlaceException(String message) {
		super(message);
	}
	
}
