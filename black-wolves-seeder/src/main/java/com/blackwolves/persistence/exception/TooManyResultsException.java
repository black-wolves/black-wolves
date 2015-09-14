package com.blackwolves.persistence.exception;

/**
 * The Class TooManyResultsException.
 * 
 * @author gastondapice
 * 
 */
public class TooManyResultsException extends DaoException{

	private static final long serialVersionUID = 8143126946474053929L;

	/**
	 * Instantiates a new too many results exception.
	 */
	public TooManyResultsException() {
		super();
	}

	/**
	 * Instantiates a new too many results exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public TooManyResultsException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new too many results exception.
	 *
	 * @param message the message
	 */
	public TooManyResultsException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new too many results exception.
	 *
	 * @param cause the cause
	 */
	public TooManyResultsException(Throwable cause) {
		super(cause);
	}
	
}
