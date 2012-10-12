package com.kurui.kums.base.database.app;

import com.kurui.kums.base.Constant;
import com.kurui.kums.base.database.SelectDataBean;

import java.io.*;

public final class JSUtil {

	public JSUtil() {
	}

	public static void buildCategory() {
		String sql = "select * from assets_category where category_level=0 order by category_code";
		String tem = "";
		SelectDataBean sdb = new SelectDataBean();
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		sql = "document.write(\"<option value=''>...</option>";
		int rowcount = sdb.getRowCount();
		for (int i = 1; i < rowcount + 1; i++) {
			tem = (new StringBuilder(String.valueOf(sdb.getColValue(i,
					"category_code")))).append("--").append(
					sdb.getColValue(i, "category_name")).toString();
			sql = (new StringBuilder(String.valueOf(sql))).append(
					"<option value='")
					.append(sdb.getColValue(i, "category_id")).append("'>")
					.append(tem).append("</option>").toString();
		}

		sql = (new StringBuilder(String.valueOf(sql))).append("\");")
				.toString();
		try {
			File file = new File((new StringBuilder(String
					.valueOf(Constant.SERVLET_PATH))).append(
					"\\_js\\category.js").toString());
			OutputStreamWriter out = new OutputStreamWriter(
					new FileOutputStream(file), "UTF-8");
			out.write(sql);
			out.close();
		} catch (Exception ex) {
			System.out.println((new StringBuilder("create category.js error:"))
					.append(ex.getMessage()).toString());
		}
	}
}
