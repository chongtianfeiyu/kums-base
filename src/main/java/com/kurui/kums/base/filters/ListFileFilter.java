package com.kurui.kums.base.filters;

import java.io.*;

public class ListFileFilter implements FileFilter {

	private String FilterDirectory[];
	private String FilterFileName[];

	public ListFileFilter(String filterDirectory[], String filterFileName[]) {
		FilterDirectory = filterDirectory;
		FilterFileName = filterFileName;
	}

	public boolean accept(File file) {
		if (file.isDirectory()) {
			String as[];
			int k = (as = FilterDirectory).length;
			for (int i = 0; i < k; i++) {
				String filterDirectory = as[i];
				if (filterDirectory.equals(file.getName())) {
					System.out.println((new StringBuilder(String.valueOf(file
							.getName()))).append("Ŀ¼�����˵�").toString());
					return false;
				}
			}

		} else {
			String as1[];
			int l = (as1 = FilterFileName).length;
			for (int j = 0; j < l; j++) {
				String fileName = as1[j];
				if (fileName.equals(file.getName())) {
					System.out.println((new StringBuilder(String.valueOf(file
							.getName()))).append("�ļ������˵�").toString());
					return false;
				}
			}

		}
		return true;
	}

	public static void main(String s[]) {
		File file = new File("D:\\zhang");
		String d[] = { "zwwlmzy", "zww" };
		String ss[] = { "zwwlmzy.txt" };
		ListFileFilter listFilter = new ListFileFilter(d, ss);
		file.listFiles(listFilter);
	}
}
