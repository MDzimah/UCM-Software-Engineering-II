package exceptions;

import misc.Messages;

@SuppressWarnings("serial")
public class UnknownObraException extends Exception {
	public UnknownObraException() {
	       super(Messages.EXC_UNKNOWN_OBRA);
	}
}
