package org.jfr.exception;

/**
 * Thrown when a Parser Exception occurs.
 *
 * @author Ian
 */
public class ParserException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public ParserException()
	{
		super();
	}

	public ParserException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ParserException(String message)
	{
		super(message);
	}
}
