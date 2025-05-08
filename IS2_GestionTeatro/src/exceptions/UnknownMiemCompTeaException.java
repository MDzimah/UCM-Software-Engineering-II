package exceptions;

import misc.Messages;

@SuppressWarnings("serial")
public class UnknownMiemCompTeaException extends Exception{
	public UnknownMiemCompTeaException() {
	       super(Messages.EXC_UNKNOWN_MIEM_COMP);
	}
}
