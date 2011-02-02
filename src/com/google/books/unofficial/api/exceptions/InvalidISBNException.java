package com.google.books.unofficial.api.exceptions;

public class InvalidISBNException extends Exception {
	private static final long serialVersionUID = 1L;
	private final String value;
	
	public InvalidISBNException(String value) {
		this.value = value;
	}
	
	@Override
	public String getMessage() {
		return "The given value " + getValue() + " is not valid ISBN.";
	}
	
	public String getValue() {
		return this.value;
	}
}
