package kr.co.sys4u.msa.core.exception;

public class AlertException extends RuntimeException {
	private static final long serialVersionUID = 2396896022000766053L;
	
	protected transient String redirectUrl;

	public AlertException() {
		super();
	}

	public AlertException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlertException(String message) {
		super(message);
	}

	public AlertException(String message, String redirectUrl, Throwable cause) {
		super(message, cause);
		this.redirectUrl = redirectUrl;
	}

	public AlertException(Throwable cause) {
		super(cause);
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
}
