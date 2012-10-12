package com.kurui.kums.base.filters;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

public class SetCharacterEncodingFilter implements Filter {

	protected String encoding;
	protected FilterConfig filterConfig;
	protected boolean ignore;
	public static ArrayList urls = null;

	public SetCharacterEncodingFilter() {
		encoding = null;
		filterConfig = null;
		ignore = true;
	}

	public void destroy() {
		encoding = null;
		filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String uri = httpRequest.getServletPath();
		
		if (uri.indexOf("/_jsp/mainTitle.jsp") > 0)
			System.out.println((new StringBuilder(
					"--------------------------OK---")).append(uri).toString());
		boolean flag = true;
		if (urls != null) {
			for (int i = 0; i < urls.size(); i++)
				flag = flag && uri.indexOf((String) urls.get(i)) < 0;

			if (flag && (ignore || request.getCharacterEncoding() == null)) {
				String encoding = selectEncoding(request);
							
				if (encoding != null)
					request.setCharacterEncoding(encoding);
			}
		}
		chain.doFilter(request, response);
	}

	public void _doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (ignore || request.getCharacterEncoding() == null) {
			String encoding = selectEncoding(request);
			if (encoding != null)
				request.setCharacterEncoding(encoding);
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		encoding = filterConfig.getInitParameter("encoding");
		
		String value = filterConfig.getInitParameter("ignore");
		
		if (value == null)
			ignore = true;
		else if (value.equalsIgnoreCase("true"))
			ignore = true;
		else if (value.equalsIgnoreCase("yes"))
			ignore = true;
		else
			ignore = false;
	}

	protected String selectEncoding(ServletRequest request) {
		return encoding;
	}

	public void setUrls(ArrayList myurls) {
		urls = myurls;
	}

}
