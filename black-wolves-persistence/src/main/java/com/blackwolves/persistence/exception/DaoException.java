package com.blackwolves.persistence.exception;

/**
 * The Class DaoException.
 * 
 * @author gastondapice
 * 
 */
public class DaoException extends Exception{

	private static final long serialVersionUID = 495726558355791674L;

	/**
	 * Instantiates a new dao exception.
	 */
	public DaoException() {
		super();
	}

	/**
	 * Instantiates a new dao exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new dao exception.
	 *
	 * @param message the message
	 */
	public DaoException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new dao exception.
	 *
	 * @param cause the cause
	 */
	public DaoException(Throwable cause) {
		super(cause);
	}

}