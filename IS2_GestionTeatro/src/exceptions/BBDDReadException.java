package exceptions;

import java.io.IOException;

@SuppressWarnings("serial")
public class BBDDReadException extends IOException {
	public BBDDReadException(String message, Throwable cause) {
	       super(message, cause);
	}
}
