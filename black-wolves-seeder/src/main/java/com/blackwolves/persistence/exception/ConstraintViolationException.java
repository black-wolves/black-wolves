package com.blackwolves.persistence.exception;

/**
 * The Class ConstraintViolationException.
 * 
 * @author gastondapice
 *
 */
public class ConstraintViolationException extends DaoException{

	private static final long serialVersionUID = -4575061391755016541L;

	/**
	 * 
	 */
	public ConstraintViolationException() {
		super();
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public ConstraintViolationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 * @param message
	 */
	public ConstraintViolationException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param cause
	 */
	public ConstraintViolationException(Throwable cause) {
		super(cause);
	}
	
}