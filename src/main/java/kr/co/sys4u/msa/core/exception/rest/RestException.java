package kr.co.sys4u.msa.core.exception.rest;

public class RestException extends RuntimeException {
	private static final long serialVersionUID = 7597216279115543111L;

	public RestException() {
		super();
	}

	public RestException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestException(String message) {
		super(message);
	}

	public RestException(Throwable cause) {
		super(cause);
	}
}
