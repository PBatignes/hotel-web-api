package dev.hotel.exception;

import dev.hotel.utils.Messages;

public class ReservationException extends HotelException {

	private Messages messages;

	/**
	 * Constructor
	 *
	 * @param messages
	 */
	public ReservationException(Messages messages) {
		super();
		this.messages = messages;
	}

	/**
	 * Getter
	 *
	 * @return the messages
	 */
	public Messages getMessages() {
		return messages;
	}

	/**
	 * Constructor
	 *
	 */
	public ReservationException() {
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
	public ReservationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 * @param cause
	 */
	public ReservationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 */
	public ReservationException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause
	 */
	public ReservationException(Throwable cause) {
		super(cause);
	}

}
