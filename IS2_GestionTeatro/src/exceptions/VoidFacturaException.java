package exceptions;

import misc.Messages;

@SuppressWarnings("serial")
public class VoidFacturaException extends Exception {
	

	public VoidFacturaException() {
		super(Messages.EXC_VOID_FACTURA);
	}

}
