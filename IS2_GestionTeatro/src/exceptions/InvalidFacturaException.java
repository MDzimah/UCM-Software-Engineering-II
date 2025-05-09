package exceptions;

import misc.Messages;

@SuppressWarnings("serial")
public class InvalidFacturaException extends Exception {
	
	public InvalidFacturaException() {
		super(Messages.EXC_INVALID_FACTURA);
	}

}
