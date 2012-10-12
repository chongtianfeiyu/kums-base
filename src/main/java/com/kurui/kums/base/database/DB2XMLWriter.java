package com.kurui.kums.base.database;

import java.io.File;
import java.io.FileWriter;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class DB2XMLWriter {

	public DB2XMLWriter() {
	}

	public static synchronized void out(Document document, String filename) {
		try {
			XMLWriter writer = null;
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GB2312");
			writer = new XMLWriter(new FileWriter(new File(filename)), format);
			writer.write(document);
			writer.close();
			System.out.println((new StringBuilder("out ")).append(filename)
					.append(" success!").toString());
		} catch (Exception ex) {
			System.out.println((new StringBuilder("out ")).append(filename)
					.append(" error:").append(ex.getMessage()).toString());
		}
	}
}
