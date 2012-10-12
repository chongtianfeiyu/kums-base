package com.kurui.kums.base.database;

import java.io.FileOutputStream;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.kurui.kums.base.Constant;
import com.kurui.kums.base.digester.Field;
import com.kurui.kums.base.digester.Table;
import com.kurui.kums.base.digester.TableDigester;

public class DB2XML {

	public DB2XML() {
	}

	public static void out() {
		try {
			TableDigester td = new TableDigester();
			for (int i = 0; i < td.getTables().getSize(); i++)
				out(td.getTables().get(i).getTableID());

		} catch (Exception ex) {
			System.out.println((new StringBuilder(
					"Error:out all xml file faile!")).append(ex.getMessage())
					.toString());
		}
	}

	public static void out(String tid) {
		String sql = "";
		String temp = "";
		TableDigester td = new TableDigester();
		Table t = td.getTables().getTable(tid);
		String tname = t.getTableName();
		Vector vFields = t.getFields();
		Field f = null;
		SelectDataBean sdb = new SelectDataBean();
		if (t.getRsCount().equals("0"))
			sql = "select ";
		else
			sql = (new StringBuilder("select top ")).append(t.getRsCount())
					.append(" ").toString();
		for (int i = 0; i < t.getFieldSize(); i++) {
			f = (Field) vFields.get(i);
			sql = (new StringBuilder(String.valueOf(sql))).append(
					f.getFieldName()).toString();
			if (i != t.getFieldSize() - 1)
				sql = (new StringBuilder(String.valueOf(sql))).append(",")
						.toString();
			else
				sql = (new StringBuilder(String.valueOf(sql))).append(" ")
						.toString();
		}

		sql = (new StringBuilder(String.valueOf(sql))).append(" from ").append(
				tname).toString();
		if (!t.getRelatedTableName().equals("")) {
			sql = (new StringBuilder(String.valueOf(sql))).append(" a join ")
					.toString();
			sql = (new StringBuilder(String.valueOf(sql))).append(
					t.getRelatedTableName()).toString();
			sql = (new StringBuilder(String.valueOf(sql))).append(" b on a.")
					.append(t.getFieldName()).toString();
			sql = (new StringBuilder(String.valueOf(sql))).append("=b.")
					.append(t.getRelatedFieldName()).toString();
		}
		if (!t.getWhereFieldName().equals(""))
			sql = (new StringBuilder(String.valueOf(sql))).append(" where ")
					.append(t.getWhereFieldName()).append("=").append(
							t.getWhereFieldValue()).toString();
		if (!t.getOrderByFieldName().equals(""))
			sql = (new StringBuilder(String.valueOf(sql))).append(" order by ")
					.append(t.getOrderByFieldName()).append(" ").append(
							t.getOrderBy()).toString();
		Element tr = new Element(t.getRootTagName());
		Document doc = new Document(tr);
		tr = doc.getRootElement();
		int rowcount = 0;
		try {
			synchronized (tid) {
				sdb.setQuerySQL(sql);
				sdb.executeQuery();
				rowcount = sdb.getRowCount();
				for (int i = 1; i < rowcount + 1; i++) {
					Element rs = new Element(t.getRsTagName());
					for (int j = 0; j < t.getFieldSize(); j++) {
						f = (Field) vFields.get(j);
						Element e = new Element(f.getFieldTagName());
						e.setText(sdb.getColValue(i, f.getFieldName()));
						rs.addContent(e);
					}

					tr.addContent(rs);
				}

				Format format = Format.getPrettyFormat();
				format.setEncoding("utf-8");
				format.setIndent("");
				format.setLineSeparator("");
				format
						.setTextMode(org.jdom.output.Format.TextMode.TRIM_FULL_WHITE);
				XMLOutputter XMLOut = new XMLOutputter(format);
				XMLOut.output(doc, new FileOutputStream((new StringBuilder(
						String.valueOf(Constant.SERVLET_XML_PATH))).append(
						t.getOutFileName()).append(".xml").toString()));
			}
		} catch (Exception ex) {
			System.out.println((new StringBuilder("xml ")).append(
					t.getOutFileName()).append(" build error:").append(
					ex.getMessage()).toString());
		}
	}

	public static void main(String args[]) {
		String path = "e:\\project\\sunes\\";
		Constant.SERVLET_XML_PATH = "D:\\project\\assets\\defaultroot\\_xml";
		Constant.SERVLET_PATH = "D:\\project\\assets\\defaultroot";
		try {
			out("department");
		} catch (Exception ex) {
			System.out.println((new StringBuilder("error:")).append(
					ex.getMessage()).toString());
		}
	}
}
