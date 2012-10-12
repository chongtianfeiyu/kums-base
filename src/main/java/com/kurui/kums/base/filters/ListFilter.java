package com.kurui.kums.base.filters;

import java.io.*;

public class ListFilter implements FilenameFilter {

	private String FilterDirectory[];
	private String FilterFileName[];
	private String FilterPostfix[];

	public ListFilter(String filterDirectory[], String filterFileName[],
			String filterPostfix[]) {
		FilterDirectory = filterDirectory;
		FilterFileName = filterFileName;
		FilterPostfix = filterPostfix;
	}

	public ListFilter(String filterDirectory[]) {
		FilterDirectory = filterDirectory;
	}

	public boolean accept(File arg0, String fileName) {
		return isDirectory(fileName) && isPostfix(fileName) && isFile(fileName);
	}

	public boolean isDirectory(String fileName) {
		String as[];
		int j = (as = FilterDirectory).length;
		for (int i = 0; i < j; i++) {
			String s = as[i];
			if (s.equals(fileName)) {
				System.out.println((new StringBuilder("����:")).append(s)
						.append("�ļ���").toString());
				return false;
			}
		}

		return true;
	}

	public boolean isFile(String fileName) {
		String as[];
		int j = (as = FilterFileName).length;
		for (int i = 0; i < j; i++) {
			String s = as[i];
			if (fileName.indexOf((new StringBuilder(String.valueOf(s))).append(
					".").toString()) != -1) {
				System.out.println((new StringBuilder("����:")).append(s)
						.append("�ļ�").toString());
				return false;
			}
		}

		return true;
	}

	public boolean isPostfix(String fileName) {
		String as[];
		int j = (as = FilterPostfix).length;
		for (int i = 0; i < j; i++) {
			String s = as[i];
			if (fileName.toLowerCase().endsWith(s)) {
				System.out.println((new StringBuilder("����:")).append(s)
						.append("��չ����ļ�").toString());
				return false;
			}
		}

		return true;
	}

	public static void main(String args[]) {
		File directory = new File("D:\\zhang\\");
		String filterDirectory[] = { "zwwlmzy", "zww" };
		String filterFileName[] = { "zwwlmzy" };
		String filterPostfix[] = { ".txt", ".jpg" };
		String images[] = directory.list(new ListFilter(filterDirectory,
				filterFileName, filterPostfix));
	}
}
