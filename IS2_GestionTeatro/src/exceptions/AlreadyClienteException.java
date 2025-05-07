package exceptions;

import misc.Messages;

public class AlreadyClienteException extends Exception{
	public AlreadyClienteException() {
		super(Messages.EXC_ALR_CLI);
	}

}
