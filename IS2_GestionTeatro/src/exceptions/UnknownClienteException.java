package exceptions;

import misc.Messages;

@SuppressWarnings("serial")
public class UnknownClienteException extends Exception {
	public UnknownClienteException() {
		super(Messages.EXC_UNKNOWN_CLI);
	}

}
