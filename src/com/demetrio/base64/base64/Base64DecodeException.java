package com.demetrio.base64.base64;

public class Base64DecodeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7610589446249681619L;
	
	public Base64DecodeException()
	{
		super("Carattere Base64 errato!!");
	}
	
	public Base64DecodeException(String msg)
	{
		super(msg);
	}
}
