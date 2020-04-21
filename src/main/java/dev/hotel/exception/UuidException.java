package dev.hotel.exception;

/**
 * Exception levé lors d'une erreur au niveau d'un uuid non trouvé ou invalide
 * 
 * @author Pierre BATIGNES
 *
 */
public class UuidException extends HotelException {

	/**
	 * Constructor
	 *
	 */
	public UuidException() {
		super();
	}

	/**
	 * Constructor
	 *
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public UuidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 * @param cause
	 */
	public UuidException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 */
	public UuidException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause
	 */
	public UuidException(Throwable cause) {
		super(cause);
	}

}
