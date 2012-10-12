package com.kurui.kums.base.filters;

import java.io.IOException;
import javax.servlet.*;

public final class ExampleFilter implements Filter {

	private String attribute;
	private FilterConfig filterConfig;

	public ExampleFilter() {
		attribute = null;
		filterConfig = null;
	}

	public void destroy() {
		attribute = null;
		filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (attribute != null)
			request.setAttribute(attribute, this);
		long startTime = System.currentTimeMillis();
		chain.doFilter(request, response);
		long stopTime = System.currentTimeMillis();
		filterConfig.getServletContext().log(
				(new StringBuilder(String.valueOf(toString()))).append(": ")
						.append(stopTime - startTime).append(" milliseconds")
						.toString());
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		attribute = filterConfig.getInitParameter("attribute");
	}

	public String toString() {
		if (filterConfig == null) {
			return "InvokerFilter()";
		} else {
			StringBuffer sb = new StringBuffer("InvokerFilter(");
			sb.append(filterConfig);
			sb.append(")");
			return sb.toString();
		}
	}
}
