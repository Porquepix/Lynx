package com.lynx.core.exception;

@SuppressWarnings("serial")
public class LynxException extends RuntimeException {

    public LynxException() {
	super();
    }

    public LynxException(String message) {
	super(message);
    }

    public LynxException(String message, Throwable t) {
	super(message, t);
    }

    public LynxException(Throwable t) {
	super(t);
    }

}
