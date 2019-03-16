package com.demetrio.base64.base64;

/** Class that extends {@link java.lang.RuntimeException RuntimeException}.
 * 	It realises an exception that can be thrown when a base64 decoding error occur.
 * 	It has two constructors: {@link #Base64DecodeException()} and {@link #Base64DecodeException(String)}
 * 	@author Alessandro Chiariello (Demetrio)
 * 	@version 1.0
 * 	@see java.lang.RuntimeException RuntimeException */
public class Base64DecodeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7610589446249681619L;
	
	/** Constructor with no argument. It calls the superclass constructor with this string as argument:<br/>
	 * 	{@code Error: invalid base64 character!!}
	 * 	@author Alessandro Chiariello (Demetrio)
	 * 	@version 1.0
	 * 	@see java.lang.RuntimeException#RuntimeException(String) RuntimeException(String) */
	public Base64DecodeException()
	{
		super("Error: invalid base64 character!!");
	}
	
	/** Constructor that calls the superclass constructor with {@code msg} as argument.
	 * 	@param msg - the string passed as argument to the superclass constructor
	 * 	@author Alessandro Chiariello (Demetrio)
	 * 	@version 1.0
	 * 	@see java.lang.RuntimeException#RuntimeException(String) RuntimeException(String) */
	public Base64DecodeException(String msg)
	{
		super(msg);
	}
}
