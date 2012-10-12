package com.kurui.kums.base.filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Category;

public class ProtectedUrlFilter implements Filter {

	private Category cat;
	private FilterConfig filterConfig;
	private String invalidUrl;

	public ProtectedUrlFilter() {
		cat = Category.getInstance(ProtectedUrlFilter.class.getName());
		filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String uri = ((HttpServletRequest) request).getRequestURI();
		if (uri.indexOf(invalidUrl) != -1) {
			String rejectLog = createRejectLog(request, uri);
			cat.warn(rejectLog);
			if (filterConfig != null)
				filterConfig.getServletContext().log(rejectLog);
			HttpServletRequest httpreq = (HttpServletRequest) request;
			HttpServletResponse httpresp = (HttpServletResponse) response;
			httpresp.sendError(403, "Protected Content.");
			return;
		} else {
			chain.doFilter(request, response);
			return;
		}
	}

	protected String createRejectLog(ServletRequest request, String uri) {
		StringBuffer rejectLog = new StringBuffer();
		rejectLog.append((new StringBuilder("!!!!!! REJECTED URI="))
				.append(uri).append(" ").toString());
		rejectLog.append((new StringBuilder("from ")).append(
				request.getRemoteAddr()).append("(").append(
				request.getRemoteHost()).append(") !!!!!!\n").toString());
		HttpServletRequest r = (HttpServletRequest) request;
		return rejectLog.toString();
	}

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		invalidUrl = filterConfig.getInitParameter("url.filter");
		if (invalidUrl == null) {
			cat
					.fatal("Unable to initialize ProtectedUrlFilter: url.filter parameter missing.");
			throw new ServletException(
					"Unable to initialize ProtectedUrlFilter: url.filter parameter missing.");
		} else {
			cat.info((new StringBuilder("ProtectedUrlFilter: input pattern="))
					.append(invalidUrl).toString());
			return;
		}
	}
}
