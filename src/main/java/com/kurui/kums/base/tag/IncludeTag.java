package com.kurui.kums.base.tag;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.kurui.kums.base.util.StringUtil;

public class IncludeTag extends BodyTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String a;
	private String all;
	private String result;

	public IncludeTag() {
		a = "";
		all = "";
		result = "";
	}

	public int doEndTag() throws JspException {
		String temp[] = result.split(",");
		if (StringUtil.isInclude(a, all)) {
			try {
				if (temp.length > 0)
					pageContext.getOut().write(temp[0]);
				else
					pageContext.getOut().write("");
			} catch (Exception xe) {
				throw new JspException(
						(new StringBuilder("Include Tag Error:")).append(
								xe.getMessage()).toString());
			}
			return 0;
		}
		try {
			if (temp.length > 1)
				pageContext.getOut().write(temp[1]);
			else
				pageContext.getOut().write("");
		} catch (Exception ex) {
			throw new JspException((new StringBuilder("Include Tag Error:"))
					.append(ex.getMessage()).toString());
		}
		return 0;
	}

	public void release() {
		super.release();
		a = "";
		all = "";
		result = "";
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		this.all = all;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}