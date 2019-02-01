package places.exceptions;

/**
 * This exception occurs when the user tries to place a city/center outside the drawing area.
 * @author Andrea Mogavero
 *
 */
public class IllegalPositionException extends Exception {

	public IllegalPositionException() {
		super("Illegal position.");
	}

	public IllegalPositionException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
