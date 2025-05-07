package exceptions;

import misc.Messages;

public class DuplicateElementException extends Exception {
	public DuplicateElementException() {
		super(Messages.EXC_ELEM_DUPLICADO);
	}
}
