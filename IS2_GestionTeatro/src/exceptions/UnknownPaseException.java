package exceptions;

import misc.Messages;

@SuppressWarnings("serial")
public class UnknownPaseException extends Exception {
	public UnknownPaseException() {
	       super(Messages.EXC_UNKNOWN_PASE);
	}
}

