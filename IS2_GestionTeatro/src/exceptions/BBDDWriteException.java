package exceptions;

import java.io.IOException;

@SuppressWarnings("serial")
public class BBDDWriteException extends IOException {
	public BBDDWriteException(String message, Throwable cause) {
	       super(message, cause);
	}
}
