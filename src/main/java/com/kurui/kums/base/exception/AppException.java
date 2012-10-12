package com.kurui.kums.base.exception;

public class AppException extends BaseException {

	public AppException() {
	}

	public AppException(Throwable throwable) {
		super(throwable);
	}

	public AppException(String s) {
		super(s);
	}

	public AppException(String s, Object aobj[]) {
		super(s, aobj);
	}

	public AppException(String s, Object aobj[], Throwable throwable) {
		super(s, aobj, throwable);
	}
}