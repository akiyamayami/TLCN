package com.tlcn.error;

public class DriverNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DriverNotFoundException(){
		super();
	}
	
	public DriverNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DriverNotFoundException(final String message) {
        super(message);
    }

    public DriverNotFoundException(final Throwable cause) {
        super(cause);
    }
}
