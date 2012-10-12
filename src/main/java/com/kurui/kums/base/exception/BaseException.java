package com.kurui.kums.base.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public abstract class BaseException extends Exception {

	protected Throwable rootCause;
	private String messageKey;
	private Object messageArgs[];

	public BaseException() {
		rootCause = null;
		messageKey = "error.unknown";
		messageArgs = null;
	}

	public BaseException(Throwable cause) {
		rootCause = null;
		messageKey = "error.unknown";
		messageArgs = null;
		rootCause = cause;
		messageArgs = (new String[] { rootCause.getMessage() });
	}

	public BaseException(String key) {
		this(key, null);
	}

	public BaseException(String key, Object args[]) {
		this(key, args, null);
	}

	public BaseException(String key, Object args[], Throwable cause) {
		rootCause = null;
		messageKey = "error.unknown";
		messageArgs = null;
		messageKey = key;
		messageArgs = args;
		rootCause = cause;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public Object[] getMessageArgs() {
		return messageArgs;
	}

	public Throwable getRootCause() {
		return rootCause;
	}

	public void setMessageArgs(Object messageArgs[]) {
		this.messageArgs = messageArgs;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}

	public void printStackTrace(PrintStream outStream) {
		printStackTrace(new PrintWriter(outStream));
	}

	public void printStackTrace(PrintWriter writer) {
		super.printStackTrace(writer);
		if (getRootCause() != null)
			getRootCause().printStackTrace(writer);
		writer.flush();
	}
}