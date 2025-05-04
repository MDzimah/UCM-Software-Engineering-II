package exceptions;

import misc.Messages;

@SuppressWarnings("serial")
public class UnknownTaquilleroException extends Exception {
	public UnknownTaquilleroException() {
		super(Messages.EXC_UNKNOWN_TAQ);
	}
}
