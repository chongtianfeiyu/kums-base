package com.kurui.kums.base.filters;

import java.io.*;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Locale;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public final class RequestDumperFilter implements Filter {

	private FilterConfig filterConfig;

	public RequestDumperFilter() {
		filterConfig = null;
	}

	public void destroy() {
		filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (filterConfig == null)
			return;
		StringWriter sw = new StringWriter();
		PrintWriter writer = new PrintWriter(sw);
		writer.println((new StringBuilder("Request Received at ")).append(
				new Timestamp(System.currentTimeMillis())).toString());
		writer.println((new StringBuilder(" characterEncoding=")).append(
				request.getCharacterEncoding()).toString());
		writer.println((new StringBuilder("     contentLength=")).append(
				request.getContentLength()).toString());
		writer.println((new StringBuilder("       contentType=")).append(
				request.getContentType()).toString());
		writer.println((new StringBuilder("            locale=")).append(
				request.getLocale()).toString());
		writer.print("           locales=");
		Enumeration locales = request.getLocales();
		boolean first = true;
		Locale locale;
		for (; locales.hasMoreElements(); writer.print(locale.toString())) {
			locale = (Locale) locales.nextElement();
			if (first)
				first = false;
			else
				writer.print(", ");
		}

		writer.println();
		for (Enumeration names = request.getParameterNames(); names
				.hasMoreElements(); writer.println()) {
			String name = (String) names.nextElement();
			writer.print((new StringBuilder("         parameter="))
					.append(name).append("=").toString());
			String values[] = request.getParameterValues(name);
			for (int i = 0; i < values.length; i++) {
				if (i > 0)
					writer.print(", ");
				writer.print(values[i]);
			}

		}

		writer.println((new StringBuilder("          protocol=")).append(
				request.getProtocol()).toString());
		writer.println((new StringBuilder("        remoteAddr=")).append(
				request.getRemoteAddr()).toString());
		writer.println((new StringBuilder("        remoteHost=")).append(
				request.getRemoteHost()).toString());
		writer.println((new StringBuilder("            scheme=")).append(
				request.getScheme()).toString());
		writer.println((new StringBuilder("        serverName=")).append(
				request.getServerName()).toString());
		writer.println((new StringBuilder("        serverPort=")).append(
				request.getServerPort()).toString());
		writer.println((new StringBuilder("          isSecure=")).append(
				request.isSecure()).toString());
		if (request instanceof HttpServletRequest) {
			writer.println("---------------------------------------------");
			HttpServletRequest hrequest = (HttpServletRequest) request;
			writer.println((new StringBuilder("       contextPath=")).append(
					hrequest.getContextPath()).toString());
			Cookie cookies[] = hrequest.getCookies();
			if (cookies == null)
				cookies = new Cookie[0];
			for (int i = 0; i < cookies.length; i++)
				writer.println((new StringBuilder("            cookie="))
						.append(cookies[i].getName()).append("=").append(
								cookies[i].getValue()).toString());

			String name;
			String value;
			for (Enumeration names = hrequest.getHeaderNames(); names
					.hasMoreElements(); writer.println((new StringBuilder(
					"            header=")).append(name).append("=").append(
					value).toString())) {
				name = (String) names.nextElement();
				value = hrequest.getHeader(name);
			}

			writer.println((new StringBuilder("            method=")).append(
					hrequest.getMethod()).toString());
			writer.println((new StringBuilder("          pathInfo=")).append(
					hrequest.getPathInfo()).toString());
			writer.println((new StringBuilder("       queryString=")).append(
					hrequest.getQueryString()).toString());
			writer.println((new StringBuilder("        remoteUser=")).append(
					hrequest.getRemoteUser()).toString());
			writer.println((new StringBuilder("requestedSessionId=")).append(
					hrequest.getRequestedSessionId()).toString());
			writer.println((new StringBuilder("        requestURI=")).append(
					hrequest.getRequestURI()).toString());
			writer.println((new StringBuilder("       servletPath=")).append(
					hrequest.getServletPath()).toString());
		}
		writer.println("=============================================");
		writer.flush();
		filterConfig.getServletContext().log(sw.getBuffer().toString());
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public String toString() {
		if (filterConfig == null) {
			return "RequestDumperFilter()";
		} else {
			StringBuffer sb = new StringBuffer("RequestDumperFilter(");
			sb.append(filterConfig);
			sb.append(")");
			return sb.toString();
		}
	}
}
