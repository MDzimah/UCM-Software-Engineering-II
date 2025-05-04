package exceptions;

import misc.Messages;

public class UnknownObraException extends Exception {
	public UnknownObraException() {
	       super(Messages.EXC_UNKNOWN_OBRA);
	}
}
