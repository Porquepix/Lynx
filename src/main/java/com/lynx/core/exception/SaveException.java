package com.lynx.core.exception;

@SuppressWarnings("serial")
public class SaveException extends LynxException {

	public SaveException() {
		super();
	}

	public SaveException(String message) {
		super(message);
	}

	public SaveException(String message, Throwable t) {
		super(message, t);
	}

	public SaveException(Throwable t) {
		super(t);
	}
	
}
