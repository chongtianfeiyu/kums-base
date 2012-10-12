package com.kurui.kums.base.right.util;

import com.kurui.kums.base.database.SelectDataBean;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class RightTreeXMLUtil {

	public RightTreeXMLUtil() {
	}

	public static String buildRightXml() {
		Element ds = new Element("DSTree");
		Document doc = new Document(ds);
		ds = doc.getRootElement();
		String sql = "";
		String temp = "";
		String tempx = "";
		int rowcount = 0;
		int srowcount = 0;
		SelectDataBean sdb = new SelectDataBean();
		SelectDataBean sdb2 = new SelectDataBean();
//		sql = "select * from role where role_status=1 and role_system=0";
		sql = "select * from role where role_status=1 ";
		
		System.out.println((new StringBuilder("sql=")).append(sql).toString());
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		rowcount = sdb.getRowCount();
		for (int i = 1; i < rowcount + 1; i++) {
			Element tempes = new Element("DSTree");
			tempes.setAttribute("text", (new StringBuilder(String.valueOf(sdb
					.getColValue(i, "role_name")))).append("(").append(
					sdb.getColValue(i, "role_description")).append(")")
					.toString());
			tempes.setAttribute("open", "false");
			tempes.setAttribute("target", "");
			tempes.setAttribute("href", "");
			temp = sdb.getColValue(i, "role_id");
			tempes.setAttribute("treeId", (new StringBuilder(String
					.valueOf(temp))).append("00").toString());
			sql = (new StringBuilder(
					"select * from role_right where right_status=1 and  role_id="))
					.append(temp).append(" order by right_code").toString();
			sdb2.setQuerySQL(sql);
			sdb2.executeQuery();
			srowcount = sdb2.getRowCount();
			for (int j = 1; j < srowcount + 1; j++) {
				Element subtempes = new Element("DSTree");
				temp = sdb2.getColValue(j, "right_key");
				tempx = (new StringBuilder(
						"&lt;html:multibox property=&quot;selectedRightItems&quot; name=&quot;ulf&quot; styleClass=&quot;cb&quot; value=&quot;"))
						.append(temp).append("&quot; /&gt;").toString();
				tempx = (new StringBuilder(
						"<input type='checkbox' name='selectedRightItems' class='cb' value='"))
						.append(temp).append("'/>").toString();
				subtempes.setAttribute("text", (new StringBuilder(String
						.valueOf(tempx))).append(
						sdb2.getColValue(j, "right_name")).append("(").append(
						sdb2.getColValue(j, "right_description")).append(")")
						.toString());
				subtempes.setAttribute("open", "false");
				subtempes.setAttribute("target", "");
				subtempes.setAttribute("href", "");
				subtempes.setAttribute("treeId", temp);
				if (!sdb2.getColValue(j, "right_code").equals("sa01"))
					tempes.addContent(subtempes);
			}

			ds.addContent(tempes);
		}

		sdb2.close();
		sdb.close();
		Format format = Format.getPrettyFormat();
		format.setEncoding("UTF-8");
		format.setIndent("");
		format.setLineSeparator("");
		format.setTextMode(org.jdom.output.Format.TextMode.TRIM_FULL_WHITE);
		XMLOutputter XMLOut = new XMLOutputter(format);
		temp = XMLOut.outputString(doc);
		return temp;
	}

	public static String buildAgentRightXml() {
		Element ds = new Element("DSTree");
		Document doc = new Document(ds);
		ds = doc.getRootElement();
		String sql = "";
		String temp = "";
		String tempx = "";
		int rowcount = 0;
		int srowcount = 0;
		SelectDataBean sdb = new SelectDataBean();
		SelectDataBean sdb2 = new SelectDataBean();
		sql = "select * from agent_role where role_status=1 and role_system=0";
		System.out.println((new StringBuilder("sql=")).append(sql).toString());
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		rowcount = sdb.getRowCount();
		for (int i = 1; i < rowcount + 1; i++) {
			Element tempes = new Element("DSTree");
			tempes.setAttribute("text", (new StringBuilder(String.valueOf(sdb
					.getColValue(i, "role_name")))).append("(").append(
					sdb.getColValue(i, "role_description")).append(")")
					.toString());
			tempes.setAttribute("open", "false");
			tempes.setAttribute("target", "");
			tempes.setAttribute("href", "");
			temp = sdb.getColValue(i, "id");
			tempes.setAttribute("treeId", (new StringBuilder(String
					.valueOf(temp))).append("00").toString());
			sql = (new StringBuilder(
					"select * from agent_role_right where right_status=1 and  role_id="))
					.append(temp).append(" order by right_code").toString();
			System.out.println((new StringBuilder("sql=")).append(sql)
					.toString());
			sdb2.setQuerySQL(sql);
			sdb2.executeQuery();
			srowcount = sdb2.getRowCount();
			for (int j = 1; j < srowcount + 1; j++) {
				Element subtempes = new Element("DSTree");
				temp = sdb2.getColValue(j, "id");
				tempx = (new StringBuilder(
						"&lt;html:multibox property=&quot;selectedRightItems&quot; name=&quot;ulf&quot; styleClass=&quot;cb&quot; value=&quot;"))
						.append(temp).append("&quot; /&gt;").toString();
				tempx = (new StringBuilder(
						"<input type='checkbox' name='selectedRightItems' class='cb' value='"))
						.append(temp).append("'/>").toString();
				subtempes.setAttribute("text", (new StringBuilder(String
						.valueOf(tempx))).append(
						sdb2.getColValue(j, "right_name")).append("(").append(
						sdb2.getColValue(j, "right_description")).append(")")
						.toString());
				subtempes.setAttribute("open", "false");
				subtempes.setAttribute("target", "");
				subtempes.setAttribute("href", "");
				subtempes.setAttribute("treeId", temp);
				if (!sdb2.getColValue(j, "right_code").equals("sa01"))
					tempes.addContent(subtempes);
			}

			ds.addContent(tempes);
		}

		sdb2.close();
		sdb.close();
		Format format = Format.getPrettyFormat();
		format.setEncoding("UTF-8");
		format.setIndent("");
		format.setLineSeparator("");
		format.setTextMode(org.jdom.output.Format.TextMode.TRIM_FULL_WHITE);
		XMLOutputter XMLOut = new XMLOutputter(format);
		temp = XMLOut.outputString(doc);
		return temp;
	}

	public static String escape(String xml) {
		xml = xml.replaceAll("&lt;", "<");
		xml = xml.replaceAll("&gt;", ">");
		return xml;
	}

	public static void main(String args[]) {
		String path = "e:\\project\\fdpay\\";
		try {
			String x = null;
			String var = x;
			System.out.println((new StringBuilder("error:")).append(
					Integer.parseInt("4a    9b 6d 46", 16)).toString());
		} catch (Exception ex) {
			System.out.println((new StringBuilder("error:")).append(
					Integer.parseInt("4a9b6d46", 16)).toString());
		}
	}
}
