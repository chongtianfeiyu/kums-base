package com.kurui.kums.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.apache.log4j.lf5.util.StreamUtils;
import org.apache.tools.ant.util.ReaderInputStream;

public class PrintDataUtil {


	public static void printData(double[][] data) {
		for (int i = 0; i < data.length; i++) {
			double[] tempData = data[i];
			for (int j = 0; j < tempData.length; j++) {
				System.out.println(tempData[j]);
			}
			System.out.println("-----------------");
		}
	}

	public static void printArrayln(int arr[]) {
		for (int i = 0; i < arr.length; i++)
			System.out.print((new StringBuilder(String.valueOf(arr[i])))
					.append(",").toString());

		System.out.print("\n");
	}

	public static void printArrayln(long arr[]) {
		for (int i = 0; i < arr.length; i++)
			System.out.print((new StringBuilder(String.valueOf(arr[i])))
					.append(",").toString());

		System.out.print("\n");
	}

	public static void printArrayln(String arr[]) {
		for (int i = 0; i < arr.length; i++)
			System.out.print((new StringBuilder(String.valueOf(arr[i])))
					.append(",").toString());

		System.out.print("\n");
	}

	public static void printArrayln(double arr[]) {
		for (int i = 0; i < arr.length; i++)
			System.out.print((new StringBuilder(String.valueOf(arr[i])))
					.append(",").toString());

		System.out.print("\n");
	}

	

	public static void printReader(Reader reader) {
		InputStream in = new ReaderInputStream(reader);
		printInputStream(in);	
	}
	
	public static void printInputStream(InputStream in) {
		byte[] bytes;
		try {
			bytes = StreamUtils.getBytes(in);
			System.out.println(new String(bytes));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
