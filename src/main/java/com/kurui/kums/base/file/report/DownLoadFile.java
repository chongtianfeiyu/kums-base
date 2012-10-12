package com.kurui.kums.base.file.report;

import java.io.*;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletResponse;

public class DownLoadFile {

	public DownLoadFile() {
	}

	public String performTask(HttpServletResponse response, File file,
			String outFileName) {
		try {
			FileInputStream bis = new FileInputStream(file);
			response.reset();
			if (response.getContentType() == null
					|| response.getContentType().equals(""))
				response.setContentType("application/unknown");
			System.out
					.println((new StringBuilder("response.getContentType()="))
							.append(response.getContentType()).toString());
			response.setHeader("Content-disposition", (new StringBuilder(
					"attachment;filename=")).append(
					URLEncoder.encode(outFileName, "UTF-8")).toString());
			OutputStream bos = response.getOutputStream();
			byte buff[] = new byte[1024];
			int readCount = 0;
			int i = 0;
			for (readCount = bis.read(buff); readCount != -1; readCount = bis
					.read(buff))
				bos.write(buff, 0, readCount);

			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		} catch (Exception e) {
			System.out.println((new StringBuilder("���ش���")).append(
					e.getMessage()).toString());
			return e.getMessage();
		}
		return "";
	}

	public String performTask(HttpServletResponse response, InputStream in,
			String outFileName) {
		try {
			BufferedInputStream bis = new BufferedInputStream(in);
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "max-age=30");
			response.setHeader("Content-disposition", (new StringBuilder(
					"attachment;filename=")).append(
					URLEncoder.encode(outFileName, "UTF-8")).toString());
			response.setContentType("application/p12");
			OutputStream bos = response.getOutputStream();
			byte buf[] = new byte[1024];
			for (int size = 0; (size = bis.read(buf)) != -1;)
				bos.write(buf, 0, size);

			bos.close();
			bis.close();
		} catch (Exception e) {
			System.out.println((new StringBuilder("���ش���")).append(
					e.getMessage()).toString());
			return e.getMessage();
		}
		return null;
	}

	public String performTask(HttpServletResponse response, String txt,
			String outFileName, String charset) {
		PrintWriter out = null;
		response.reset();
		response.setContentType("text/plain");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=30");
		try {
			response.setHeader("Content-disposition", (new StringBuilder(
					"attachment;filename=")).append(
					URLEncoder.encode(outFileName, "UTF-8")).toString());
			out = new PrintWriter(new OutputStreamWriter(response
					.getOutputStream(), charset));
			out.println(txt);
			out.flush();

			if (out != null)
				try {
					out.close();
				} catch (Exception exception1) {
				}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String performTask(HttpServletResponse response, byte bytes[],
			String outFileName) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			DataInputStream bis = new DataInputStream(bais);
			response.reset();
			response.setContentType("text/plain");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "max-age=30");
			response.setHeader("Content-disposition", (new StringBuilder(
					"attachment;filename=")).append(
					URLEncoder.encode(outFileName, "UTF-8")).toString());
			OutputStream bos = response.getOutputStream();
			byte buff[] = new byte[1024];
			int readCount = 0;
			for (readCount = bis.read(buff); readCount != -1; readCount = bis
					.read(buff))
				bos.write(buff, 0, readCount);

			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
			if (bais != null)
				bais.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
		return "";
	}

	public String performTask(HttpServletResponse response,
			ByteArrayOutputStream baos, String outFileName) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(baos
					.toByteArray());
			response.reset();
			response.setContentType("text/plain");
			response.setHeader("Content-disposition", (new StringBuilder(
					"attachment;filename=")).append(
					URLEncoder.encode(outFileName, "UTF-8")).toString());
			OutputStream bos = response.getOutputStream();
			byte buff[] = new byte[1024];
			int readCount = 0;
			for (readCount = bais.read(buff); readCount != -1; readCount = bais
					.read(buff))
				bos.write(buff, 0, readCount);

			if (bais != null)
				bais.close();
			if (bos != null)
				bos.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
		return "";
	}
}
