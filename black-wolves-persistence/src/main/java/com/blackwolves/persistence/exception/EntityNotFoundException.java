package com.blackwolves.persistence.exception;

/**
 * The Class EntityNotFoundException.
 * 
 * @author gastondapice
 * 
 */
public class EntityNotFoundException extends DaoException {

	private static final long serialVersionUID = -5042960336646969105L;

	/**
	 * Instantiates a new entity not found exception.
	 */
	public EntityNotFoundException() {
		super();
	}

	/**
	 * Instantiates a new entity not found exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new entity not found exception.
	 *
	 * @param message the message
	 */
	public EntityNotFoundException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new entity not found exception.
	 *
	 * @param cause the cause
	 */
	public EntityNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
