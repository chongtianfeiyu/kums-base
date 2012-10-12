package com.kurui.kums.base.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import com.kurui.kums.base.util.CurrentDate;
import com.kurui.kums.base.util.DateUtil;
import com.kurui.kums.base.encrypt.EncryptUtil;

public class FileUtil {

	public static String createFolder(String folderPath) {
		String txt = folderPath;
		try {
			File myFilePath = new File(txt);
			txt = folderPath;
			if (!myFilePath.exists())
				myFilePath.mkdir();
		} catch (Exception e) {
			return e.getMessage();
		}
		return txt;
	}

	public static String createFolders(String folderPath, String paths) {
		String txts = folderPath;
		try {
			txts = folderPath;
			StringTokenizer st = new StringTokenizer(paths, "|");
			for (int i = 0; st.hasMoreTokens(); i++) {
				String txt = st.nextToken().trim();
				if (txts.lastIndexOf("/") != -1)
					txts = createFolder((new StringBuilder(String.valueOf(txts)))
							.append(txt).toString());
				else
					txts = createFolder((new StringBuilder(String.valueOf(txts)))
							.append(txt).append("/").toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return txts;
	}

	public static  void createFile(String filePathAndName, String fileContent) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists())
				myFilePath.createNewFile();
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
			resultFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createFile(String filePathAndName, String fileContent,
			String encoding) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists())
				myFilePath.createNewFile();
			PrintWriter myFile = new PrintWriter(myFilePath, encoding);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 日志文件增量处理方法
	 * 
	 * @param String
	 *            初始文件
	 * @param int
	 *            增量大小(kb)
	 */

	public static void autoIncrement(String file, int incrementSize) {

		int bytesum = 0;
		int byteread = 0;
		File oldFile = new File(file);

		if (!oldFile.exists()) {
			return;
		}
		InputStream inStream = null;
		String newFile = "";

		try {
			inStream = new FileInputStream(file);
			int oldFileSize = inStream.available() / 1024;// byte size

			if (oldFileSize > incrementSize) { // 如果文件内容超出设置范围
				// 复制日志内容到新文件
				if (oldFile.exists()) {
					newFile = getNewFileName(file);
					FileOutputStream fs = new FileOutputStream(newFile);
					byte[] buffer = new byte[1444];
					// int length;
					while ((byteread = inStream.read(buffer)) != -1) {
						bytesum += byteread; // 字节数 文件大小
						// System.out.println(bytesum);
						fs.write(buffer, 0, byteread);
					}
					inStream.close();
					// 删除旧文件
					oldFile.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取新的文件名
	 */
	public static String getNewFileName(String oldfilePath) {
		String head = oldfilePath.substring(0, oldfilePath.length() - 4);
		String nowTime = DateUtil.getDateString("HHMMss");
		String newFilePath = head + "." + nowTime + ".log";
		return newFilePath;
	}

	/**
	 * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	 * 
	 * @param fileName
	 *            文件名
	 */
	public static void readFileByChars(String fileName) {
		File file = new File(fileName);
		Reader reader = null;
		try {
			System.out.println("以字符为单位读取文件内容，一次读一个字节：");
			// 一次读一个字符
			reader = new InputStreamReader(new FileInputStream(file));
			int tempchar;
			while ((tempchar = reader.read()) != -1) {
				System.out.print((char) tempchar);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取文件大小
	 */
	public static int getFileSize(String file) {
		File myLogFile = new File(file);

		return getFileSize(myLogFile);
	}

	public static int getFileSize(File file) {
		int size = 0;
		FileInputStream inFile = null;
		try {
			inFile = new FileInputStream(file);

			size = inFile.available();// byte size

		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	public  static String readTxt(String filePathAndName, String encoding)
			throws IOException {
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer("");
		String st = "";
		try {
			FileInputStream fs = new FileInputStream(filePathAndName);
			InputStreamReader isr;
			if (encoding.equals(""))
				isr = new InputStreamReader(fs);
			else
				isr = new InputStreamReader(fs, encoding);
			BufferedReader br = new BufferedReader(isr);
			try {
				for (String data = ""; (data = br.readLine()) != null;)
					str.append((new StringBuilder(String.valueOf(data)))
							.append(" ").toString());

			} catch (Exception e) {
				str.append(e.toString());
			}
			st = str.toString();
		} catch (IOException es) {
			st = "";
		}
		return st;
	}

	// =========================删除文件===========

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public  static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			myDelFile.delete();
		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();
		}
	}

	public  static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  static boolean delAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!file.exists())
			return bea;
		if (!file.isDirectory())
			return bea;
		String tempList[] = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator))
				temp = new File((new StringBuilder(String.valueOf(path)))
						.append(tempList[i]).toString());
			else
				temp = new File((new StringBuilder(String.valueOf(path)))
						.append(File.separator).append(tempList[i]).toString());
			if (temp.isFile())
				temp.delete();
			if (temp.isDirectory()) {
				delAllFile((new StringBuilder(String.valueOf(path)))
						.append("/").append(tempList[i]).toString());
				delFolder((new StringBuilder(String.valueOf(path))).append("/")
						.append(tempList[i]).toString());
				bea = true;
			}
		}

		return bea;
	}

	// ----------------------复制文件--------------------------------
	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				// int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
	}

	public static  void copyFolder(String oldPath, String newPath) {
		try {
			(new File(newPath)).mkdirs();
			File a = new File(oldPath);
			String file[] = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator))
					temp = new File(
							(new StringBuilder(String.valueOf(oldPath)))
									.append(file[i]).toString());
				else
					temp = new File(
							(new StringBuilder(String.valueOf(oldPath)))
									.append(File.separator).append(file[i])
									.toString());
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(
							(new StringBuilder(String.valueOf(newPath)))
									.append("/").append(
											temp.getName().toString())
									.toString());
					byte b[] = new byte[5120];
					int len;
					while ((len = input.read(b)) != -1)
						output.write(b, 0, len);
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory())
					copyFolder((new StringBuilder(String.valueOf(oldPath)))
							.append("/").append(file[i]).toString(),
							(new StringBuilder(String.valueOf(newPath)))
									.append("/").append(file[i]).toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static  void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	public void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	public static String getContentType(String exFileName) {
		Hashtable ht = new Hashtable();
		ht.put("BMP", "image/bmp");
		ht.put("GIF", "image/gif");
		ht.put("JPEG", "image/jpeg");
		ht.put("TIFF", "image/tiff");
		ht.put("DCX", "image/x-dcx");
		ht.put("PCX", "image/x-pcx");
		ht.put("HTML", "image/bmp");
		ht.put("TXT", "text/plain");
		ht.put("XML", "text/xml");
		ht.put("AFP", "application/afp");
		ht.put("PDF", "application/pdf");
		ht.put("RTF", "application/rtf");
		ht.put("MSWORD", "application/msword");
		ht.put("MSEXCEL", "application/vnd.ms-excel");
		ht.put("MSPOWERPOINT", "application/vnd.ms-powerpoint");
		ht.put("WORDPERFECT", "application/wordperfect5.1");
		ht.put("WORDPRO", "application/vnd.lotus-wordpro");
		ht.put("VISIO", "application/vnd.visio");
		ht.put("FRAMEMAKER", "application/vnd.framemaker");
		ht.put("LOTUS123", "application/vnd.lotus-1-2-3");
		return (String) ht.get(exFileName.toUpperCase());
	}

	public static synchronized String createCSVFile(ArrayList lists) {
		StringBuffer sb = new StringBuffer();
		try {
			System.out
					.println("======================填充CSV=======================");
			long a = System.currentTimeMillis();
			String temp = "";
			for (int i = 0; i < lists.size(); i++) {
				ArrayList list = (ArrayList) lists.get(i);
				for (int j = 0; j < list.size(); j++) {
					Object obj = list.get(j);
					if (obj == null)
						temp = "";
					else
						temp = obj.toString();
					if (temp.indexOf(",") >= 0)
						temp = (new StringBuilder("'")).append(temp)
								.append("'").toString();
					if (temp.indexOf("'") >= 0)
						temp = temp.replace("[']", "\"");
					if (j == list.size() - 1)
						sb.append(temp);
					else
						sb.append((new StringBuilder(String.valueOf(temp)))
								.append(",").toString());
				}

				sb.append("\r\n");
			}

			long b = System.currentTimeMillis();
			System.out.println((new StringBuilder(
					"-----------------------填充CSV时间:")).append((b - a) / 1000L)
					.toString());
			long c = System.currentTimeMillis();
			System.out.println((new StringBuilder(
					"-----------------------writecsv时间:")).append(
					(c - b) / 1000L).toString());
			System.out
					.println("======================填充csv结束========================");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sb.toString();
	}

	public static String getVFileName(String fileName) {
		CurrentDate cd = new CurrentDate();
		String ram = "";
		String xname = fileName.substring(fileName.lastIndexOf("."), fileName
				.length());
		fileName = (new StringBuilder(String.valueOf(String.valueOf(System
				.currentTimeMillis())))).append(EncryptUtil.getRandom(10, 99))
				.toString();
		int len = fileName.length();
		if (len > 5)
			fileName = fileName.substring(0, 5);
		else if (len == 1)
			ram = (new StringBuilder(String.valueOf(fileName))).append(
					EncryptUtil.getRandom(1000, 9999)).toString();
		else if (len == 2)
			ram = (new StringBuilder(String.valueOf(fileName))).append(
					EncryptUtil.getRandom(100, 999)).toString();
		else if (len == 3) {
			ram = (new StringBuilder(String.valueOf(fileName))).append(
					EncryptUtil.getRandom(10, 99)).toString();
		} else {
			System.out.println((new StringBuilder("fileName="))
					.append(fileName).toString());
			ram = (new StringBuilder(String.valueOf(fileName.substring(0, 3))))
					.append(EncryptUtil.getRandom(10, 99)).toString();
		}
		return (new StringBuilder(String.valueOf(fileName))).append(
				cd.getSixDate()).append(EncryptUtil.getRandom(10, 99)).append(
				xname).toString();
	}
}
