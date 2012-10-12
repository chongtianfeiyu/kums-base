package com.kurui.kums.base.tag;

import com.kurui.kums.base.right.BaseRightInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class URIRight extends BodyTagSupport {

	private String code;
	private BaseRightInfo uri;

	public URIRight() {
		code = "";
		uri = null;
	}

	public int doAfterBody() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		uri = (BaseRightInfo) request.getSession().getAttribute("URI");
		if (uri == null)
			return 0;
		String codes[] = code.split(",");
		boolean b = false;
		for (int i = 0; i < codes.length; i++)
			b = b || uri.hasRight(codes[i]);

		if (b) {
			BodyContent bc = getBodyContent();
			if (bc != null)
				try {
					JspWriter out = getPreviousOut();
					out.write(bc.getString());
				} catch (Exception xe) {
					throw new JspException((new StringBuilder(
							"URIRight Tag Error:")).append(xe.getMessage())
							.toString());
				}
			else
				try {
					JspWriter out = getPreviousOut();
					out.write("");
				} catch (Exception ex) {
					throw new JspException((new StringBuilder(
							"URIRight Tag Error:")).append(ex.getMessage())
							.toString());
				}
			return 0;
		}
		try {
			pageContext.getOut().print("");
		} catch (Exception ex) {
			throw new JspException((new StringBuilder("URIRight Tag Error:"))
					.append(ex.getMessage()).toString());
		}
		return 0;
	}

	public int doEndTag() throws JspException {
		return 0;
	}

	public void release() {
		super.release();
		code = "";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}