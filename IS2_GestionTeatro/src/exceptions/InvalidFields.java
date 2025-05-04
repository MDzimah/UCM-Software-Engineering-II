package exceptions;

import misc.Messages;

public class InvalidFields extends Exception {
	public InvalidFields() {
		super(Messages.EXC_INV_FIELDS);
	}
}
