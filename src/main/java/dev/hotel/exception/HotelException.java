package dev.hotel.exception;

public class HotelException extends RuntimeException {

	/**
	 * Constructor
	 *
	 */
	public HotelException() {
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
	public HotelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 * @param cause
	 */
	public HotelException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 */
	public HotelException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause
	 */
	public HotelException(Throwable cause) {
		super(cause);
	}

}
