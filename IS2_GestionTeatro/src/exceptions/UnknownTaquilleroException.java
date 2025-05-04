package exceptions;

import misc.Messages;

public class UnknownTaquilleroException extends Exception {
	public UnknownTaquilleroException() {
		super(Messages.EXC_UNKNOWN_TAQ);
	}
}
