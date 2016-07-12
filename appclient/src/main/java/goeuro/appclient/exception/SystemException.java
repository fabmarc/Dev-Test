package goeuro.appclient.exception;

public class SystemException extends RuntimeException {

	private static final long serialVersionUID = 8545933338703677305L;
	
	public SystemException(String message, Exception cause) {
		super(message, cause);
	}

	public SystemException(String message) {
		super(message);
	}

}
