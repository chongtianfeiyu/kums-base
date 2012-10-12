package com.kurui.kums.base.digester;

import com.kurui.kums.base.Constant;

import java.io.*;
import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

public class TableDigester {

	private Tables a;

	public TableDigester() {
		try {
			ParseXML(Constant.SERVLET_PATH);
		} catch (Exception ex) {
			System.out.println((new StringBuilder(
					"pause \\WEB-INF\\struts-table.xml error:")).append(
					ex.getMessage()).toString());
		}
	}

	public void ParseXML(String path) throws IOException, SAXException {
		try {
			Digester digester = new Digester();
			digester.addObjectCreate("table-config", Tables.class.getName());
			digester.addObjectCreate("table-config/table", Table.class
					.getName());
			digester
					.addSetProperties("table-config/table", "name", "tableName");
			digester.addSetProperties("table-config/table", "id", "tableID");
			digester.addSetProperties("table-config/table", "field",
					"fieldName");
			digester.addSetProperties("table-config/table", "related-table",
					"relatedTableName");
			digester.addSetProperties("table-config/table", "related-field",
					"relatedFieldName");
			digester.addBeanPropertySetter("table-config/table/title", "title");
			digester.addBeanPropertySetter("table-config/table/xmlRoot",
					"rootTagName");
			digester.addBeanPropertySetter("table-config/table/xmlRS",
					"rsTagName");
			digester.addBeanPropertySetter("table-config/table/xmlRSCount",
					"rsCount");
			digester.addObjectCreate("table-config/table/field", Field.class
					.getName());
			digester.addSetProperties("table-config/table/field", "name",
					"fieldName");
			digester.addBeanPropertySetter("table-config/table/field",
					"fieldTagName");
			digester.addSetProperties("table-config/table/field", "cityID",
					"cityID");
			digester.addSetProperties("table-config/table/field", "cityCode",
					"cityCode");
			digester.addSetProperties("table-config/table/field", "agentName",
					"agentName");
			digester.addSetNext("table-config/table/field", "addField");
			digester.addSetProperties("table-config/table/where", "name",
					"whereFieldName");
			digester.addSetProperties("table-config/table/orderBy", "name",
					"orderByFieldName");
			digester.addBeanPropertySetter("table-config/table/orderBy",
					"orderBy");
			digester.addBeanPropertySetter("table-config/table/where",
					"whereFieldValue");
			digester.addBeanPropertySetter("table-config/table/outFileName",
					"outFileName");
			digester.addSetNext("table-config/table", "addTable");
			File input = new File((new StringBuilder(String.valueOf(path)))
					.append("\\WEB-INF\\struts-table.xml").toString());
			a = (Tables) digester.parse(input);
			System.out.println("parse xml success!");
		} catch (Exception ex) {
			System.out.println((new StringBuilder("error:")).append(
					ex.getMessage()).toString());
		}
	}

	public Tables getTables() {
		return a;
	}

	public static void main(String args[]) {
		TableDigester ad = new TableDigester();
		try {
			System.out.println((new StringBuilder("city=")).append(
					ad.getTables().getTable("st_user").getOutFileName())
					.toString());
		} catch (Exception ex) {
			System.out.println((new StringBuilder("error:")).append(
					ex.getMessage()).toString());
		}
	}
}
