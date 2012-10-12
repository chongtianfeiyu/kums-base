package com.kurui.kums.base.database;

public class DataBaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public DataBaseException(String message) {
		this.message = "";
		this.message = message;
	}

	public String getMessage() {
		return (new StringBuilder(String.valueOf(message))).append(
				"above here is sdb exception").toString();
	}
}
