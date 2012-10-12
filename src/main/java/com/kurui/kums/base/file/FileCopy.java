package com.kurui.kums.base.file;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.kurui.kums.base.filters.ListFilter;

public class FileCopy {

	private String fromDir;
	private String toDir;
	private String timeDir;
	private String skipName[] = { "svn", "test" };
	private ListFilter listFilter;

	public ListFilter getListFilter() {
		return listFilter;
	}

	public void setListFilter(ListFilter listFilter) {
		this.listFilter = listFilter;
	}

	public FileCopy() {
		fromDir = "";
		toDir = "";
		timeDir = "";
	}

	public void copyFile() {
		File file = new File(fromDir);
		createFile(toDir, Boolean.valueOf(false));
		if (file.isDirectory()) {
			copyFileToDir(toDir, listFile(file, listFilter));
			createTimeFile();
		}
	}

	public void createTimeFile() {
		File time = new File(timeDir, "copyTime.txt");
		if (time.exists())
			try {
				time.delete();
				time.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		else
			try {
				time.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		try {
			FileOutputStream fos = new FileOutputStream(time);
			Calendar c = Calendar.getInstance();
			SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = d.format(c.getTime());
			byte buffer[] = now.getBytes();
			fos.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean compare(File file) {
		boolean blag = true;
		File time = new File((new StringBuilder(String.valueOf(timeDir)))
				.append("\\copyTime.txt").toString());
		if (!time.exists())
			return false;
		try {
			BufferedReader br = new BufferedReader(new FileReader(time));
			String copytime = "";
			if ((copytime = br.readLine()) != null) {
				SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date copy = null;
				try {
					copy = d.parse(copytime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Date filedate = new Date(file.lastModified());
				if (copy.getTime() < filedate.getTime())
					blag = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return blag;
	}

	public void copyFileOnly(String targetDir, String path) {
		File file = new File(path);
		File targetFile = new File(targetDir);
		if (file.isDirectory()) {
			File files[] = file.listFiles();
			File afile[];
			int j = (afile = files).length;
			for (int i = 0; i < j; i++) {
				File subFile = afile[i];
				if (subFile.isFile())
					copyFile(targetFile, subFile);
			}

		}
	}

	public void copyFileToDir(String targeDir, String filePath[]) {
		if (targeDir == null || "".equals(targeDir)) {
			System.out.println("目标路径不能空");
			return;
		}
		File targeFile = new File(targeDir);
		if (!targeFile.exists())
			targeFile.mkdir();
		else if (!targeFile.isDirectory()) {
			System.out.println("目标路径指向的不是一个目录");
			return;
		}
		String as[];
		int j = (as = filePath).length;
		for (int i = 0; i < j; i++) {
			String path = as[i];
			File file = new File(path);
			if (file.isDirectory())
				copyFileToDir((new StringBuilder(String.valueOf(targeDir)))
						.append(File.separator).append(file.getName())
						.toString(), listFile(file, listFilter));
			else
				copyFileToDir(targeDir, file, "");
		}

	}

	public void copyFileToDir(String targeDir, File file, String newName) {
		String newFile = "";
		if (newName != null && !"".equals(newName))
			newFile = (new StringBuilder(String.valueOf(targeDir))).append(
					File.separator).append(newName).toString();
		else
			newFile = (new StringBuilder(String.valueOf(targeDir))).append(
					File.separator).append(file.getName()).toString();
		File tFile = new File(newFile);
		copyFile(tFile, file);
	}

	public void copyFile(File targetFile, File file) {
		boolean blag = compare(file);
		if (targetFile.exists()) {
			if (blag)
				return;
			System.out.println((new StringBuilder("�����ļ�  ")).append(
					file.getAbsolutePath()).toString());
			targetFile.delete();
			try {
				targetFile.createNewFile();
				InputStream is = new FileInputStream(file);
				FileOutputStream fos = new FileOutputStream(targetFile);
				for (byte buffer[] = new byte[1024]; is.read(buffer) != -1; fos
						.write(buffer))
					;
				is.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (blag) {
				System.out.println((new StringBuilder("�ļ�")).append(
						targetFile.getAbsolutePath()).append("�Ѿ�ɾ�������ļ�??")
						.toString());
				return;
			}
			createFile(targetFile, Boolean.valueOf(true));
			System.out.println(file.getAbsolutePath());
			try {
				InputStream is = new FileInputStream(file);
				FileOutputStream fos = new FileOutputStream(targetFile);
				for (byte buffer[] = new byte[1024]; is.read(buffer) != -1; fos
						.write(buffer))
					;
				is.close();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void copyDir(String targetDir, String path) {
		File targetFile = new File(targetDir);
		createFile(targetFile, Boolean.valueOf(false));
		File file = new File(path);
		if (targetFile.isDirectory() && file.isDirectory())
			copyFileToDir((new StringBuilder(String.valueOf(targetFile
					.getAbsolutePath()))).append(File.separator).append(
					file.getName()).toString(), listFile(file, listFilter));
	}

	public static String[] listFile(File dir, ListFilter listFilter) {
		String absolutPath = dir.getAbsolutePath();
		String paths[] = dir.list(listFilter);
		String files[] = new String[paths.length];
		for (int i = 0; i < paths.length; i++)
			files[i] = (new StringBuilder(String.valueOf(absolutPath))).append(
					File.separator).append(paths[i]).toString();

		return files;
	}

	public void createFile(String path, Boolean isFile) {
		createFile(new File(path), isFile);
	}

	public void createFile(File file, Boolean isFile) {
		if (!file.exists())
			if (!file.getParentFile().exists())
				createFile(file.getParentFile(), Boolean.valueOf(false));
			else if (isFile.booleanValue())
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			else
				file.mkdir();
	}

	public String getFromDir() {
		return fromDir;
	}

	public void setFromDir(String fromDir) {
		this.fromDir = fromDir;
	}

	public String getToDir() {
		return toDir;
	}

	public void setToDir(String toDir) {
		this.toDir = toDir;
	}

	public String getTimeDir() {
		return timeDir;
	}

	public void setTimeDir(String timeDir) {
		this.timeDir = timeDir;
	}

	public static void main(String args[]) {
		FileCopy fc = new FileCopy();
		String filterDirectory[] = { ".svn" };
		String filterFileName[] = new String[0];
		String filterPostfix[] = new String[0];
		ListFilter filter = new ListFilter(filterDirectory, filterFileName,
				filterPostfix);
		fc.setListFilter(filter);
		fc.setFromDir("E:\\project\\fdpay-client\\defaultroot");
		fc.setToDir("E:\\project\\fdpay-client.war");
		fc.setTimeDir("d:\\");
		fc.copyFile();
	}
}
