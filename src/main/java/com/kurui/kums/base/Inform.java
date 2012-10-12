package com.kurui.kums.base;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

public class Inform implements Cloneable {
	public class TitleValueValue {

		String title;
		String value1;
		String value2;
		final Inform this$0;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getValue1() {
			return value1;
		}

		public void setValue1(String value1) {
			this.value1 = value1;
		}

		public String getValue2() {
			return value2;
		}

		public void setValue2(String value2) {
			this.value2 = value2;
		}

		public TitleValueValue(String title, String value1, String value2) {
			this$0 = Inform.this;
			// super();
			this.title = "";
			this.value1 = "";
			this.value2 = "";
			this.title = title;
			this.value1 = value1;
			this.value2 = value2;
		}
	}

	private int id;
	private String message;
	private String paramId;
	private String paramValue;
	private boolean back;
	private boolean close;
	private String blank;
	private String forwardPage;
	private String blankForwardPage;
	private HashMap params;
	private ArrayList listRadio;

	public Inform() {
		id = 0;
		message = "";
		paramId = "";
		paramValue = "";
		back = false;
		close = false;
		blank = "";
		forwardPage = "";
		blankForwardPage = "";
		params = new HashMap();
		listRadio = new ArrayList();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message.replaceAll("\n", "<br>");
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getForwardPage() {
		return forwardPage;
	}

	public void setForwardPage(String forwardPage) {
		this.forwardPage = forwardPage;
	}

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public boolean isBack() {
		return back;
	}

	public void setBack(boolean back) {
		this.back = back;
	}

	public boolean isClose() {
		return close;
	}

	public void setClose(boolean close) {
		this.close = close;
	}

	public void addParam(String key, String value) {
		params.put(key, value);
	}

	public void addRadio(String title, String value1, String value2) {
		listRadio.add(new TitleValueValue(title, value1, value2));
	}

	public ArrayList getListRadio() {
		return listRadio;
	}

	public HashMap getParams() {
		return params;
	}

	public String getBlank() {
		return blank;
	}

	public void setBlank(String blank) {
		this.blank = blank;
	}

	public String getBlankForwardPage() {
		return blankForwardPage;
	}

	public void setBlankForwardPage(String blankForwardPage) {
		this.blankForwardPage = blankForwardPage;
	}

	public void setRequest(HttpServletRequest request) {
		request.setAttribute("inf", this);
		if (params.size() > 0)
			request.setAttribute("params", params);
		if (listRadio.size() > 0)
			request.setAttribute("listRadio", listRadio);
	}

	public void reset() {
		id = 0;
		message = "";
		paramId = "";
		paramValue = "";
		back = false;
		blank = "";
		close = false;
		forwardPage = "";
		params = new HashMap();
		listRadio = new ArrayList();
	}
}
