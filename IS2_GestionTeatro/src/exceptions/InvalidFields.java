package exceptions;

import misc.Messages;

public class InvalidFields extends Exception {
	public InvalidFields() {
		super(Messages.EXC_CAMPOS_INCORRECTOS);
	}
}
