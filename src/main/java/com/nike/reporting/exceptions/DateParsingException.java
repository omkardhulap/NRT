package com.nike.reporting.exceptions;

public class DateParsingException extends NikeException {

	private static final long serialVersionUID = 1L;

	public DateParsingException() {
	}

	public DateParsingException(String errCode, String errMsg) {
		super(errCode, errMsg);
	}

	public DateParsingException(Throwable ex, String errorMsg) {
		super(ex, errorMsg);
	}

}
