package com.kurui.kums.base.file.report;

import com.kurui.kums.base.Constant;

import java.io.File;
import java.sql.Connection;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperCompileManager;

public class ReportCompile {

	Connection conn;
	String file;

	public ReportCompile() {
		file = "";
	}

	public void compile() {
		String path = (new StringBuilder(String.valueOf(Constant.SERVLET_PATH)))
				.append("/jasper/").append(file).append(".jrxml").toString();
		String newPath = path.replaceAll("jrxml", "jasper");
		File f1 = new File(path);
		if (!f1.exists())
			throw new JRRuntimeException(
					(new StringBuilder("File "))
							.append(file)
							.append(
									".jrxml not found. The report design must be compiled first.")
							.toString());
		File f2 = new File(newPath);
		if (!f2.exists()) {
			System.out.println((new StringBuilder(String.valueOf(newPath)))
					.append(" isn't exist").toString());
		} else {
			Long lngJrxmlLastModified = new Long(f1.lastModified());
			Long lngJasperLastModified = new Long(f2.lastModified());
			if (lngJasperLastModified != null
					&& lngJasperLastModified.longValue() <= lngJrxmlLastModified
							.longValue())
				try {
					JasperCompileManager.compileReportToFile(path, newPath);
				} catch (Exception ex) {
					System.out.println((new StringBuilder("compile ")).append(
							newPath).append("error! ").append(ex.getMessage())
							.toString());
				}
		}
	}
}
