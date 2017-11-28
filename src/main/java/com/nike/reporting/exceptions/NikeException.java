/**
 * @author Sachin_Ainapure
 */
package com.nike.reporting.exceptions;

public class NikeException extends Exception {

	private static final long serialVersionUID = -1559734758470136556L;

	private String errCode;
	private String errMsg;

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public NikeException() {
	}

	public NikeException(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public NikeException(Throwable ex, String errorMsg) {
		super(ex);
		this.errMsg = errorMsg;
	}

}
