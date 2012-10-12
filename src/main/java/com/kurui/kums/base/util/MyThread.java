package com.kurui.kums.base.util;

public class MyThread implements Runnable {

	int count;
	int number;

	public MyThread(int num) {
		count = 1;
		number = num;
		System.out.println((new StringBuilder("??????? �����߳�"))
				.append(number).toString());
	}

	public void run() {
		do
			System.out.println((new StringBuilder("??? ")).append(number)
					.append(":???? ").append(count).toString());
		while (++count != 6);
	}

	public static void main(String args[]) {
		for (int i = 0; i < 5; i++)
			(new Thread(new MyThread(i + 1))).start();

	}
}
