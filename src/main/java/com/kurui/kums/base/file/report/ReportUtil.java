package com.kurui.kums.base.file.report;

import com.kurui.kums.base.Constant;
import com.kurui.kums.base.database.SelectDataBean;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;
import net.sf.jasperreports.engine.JasperRunManager;

public class ReportUtil extends HttpServlet {

	public ReportUtil() {
	}

	public void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println((new StringBuilder(String
				.valueOf(Constant.SERVLET_PATH))).append(
				"\\report\\testRpt.jasper").toString());
		try {
			SelectDataBean sdb = new SelectDataBean();
			java.sql.Connection conn = sdb.getConnection();
			File reportFile = new File((new StringBuilder(String
					.valueOf(Constant.SERVLET_PATH))).append(
					"report\\testRpt.jasper").toString());
			String assetsId = "";
			assetsId = "2";
			Map parameters = new HashMap();
			parameters.put("assetsId", assetsId);
			byte bytes[] = JasperRunManager.runReportToPdf(
					reportFile.getPath(), parameters, conn);
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception ex) {
			System.out.print((new StringBuilder("out report error:")).append(
					ex.getMessage()).toString());
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		performTask(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		performTask(request, response);
	}

	public String getServletInfo() {
		return "Short description";
	}
}
