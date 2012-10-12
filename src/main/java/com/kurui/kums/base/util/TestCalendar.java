package com.kurui.kums.base.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TestCalendar {

	public static String begin = "";
	public static String end = "";
	public static String now = new java.sql.Date(new Date().getTime())
			.toString();

	public static void main(String[] args) {
		// 今天
		calcToday(begin, end, now, new GregorianCalendar());
		// 昨天
		calcYesterday(begin, end, now, new GregorianCalendar());
		// 本周
		calcThisWeek(begin, end, now, new GregorianCalendar());
		// 上周
		calcLastWeek(begin, end, now, new GregorianCalendar());
		// 本月
		calcThisMonth(begin, end, now, new GregorianCalendar());
		// 上月
		calcLastMonth(begin, end, now, new GregorianCalendar());
	}
	
	public static void testCalendar() {
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		calendar.add(Calendar.DATE, -30); // 得到前一天

		String yestedayDate = new SimpleDateFormat("yyyy-MM-dd")
				.format(calendar.getTime());

		System.out.println("yestedayDate:" + yestedayDate);

		calendar.add(Calendar.MONTH, -1); // 得到前一个月
		int year = calendar.get(Calendar.YEAR);
		System.out.println("year:" + year);

		int month = calendar.get(Calendar.MONTH) + 1; // 输出前一月的时候要记得加1

		System.out.println("month:" + month);
		
		
		calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2011);
		calendar.set(Calendar.MONTH, 10 - 1);
		calendar.set(Calendar.DATE, 10);		
		
		//得到输入的日期是一周的第几天
        int dow = calendar.get(Calendar.DAY_OF_WEEK);
        //得到一周的第一天，也就是星期天的日期
        calendar.add(Calendar.DATE, dow - 7);
        
        for (int i = 0; i < 7; i++) {
            System.out.println("一周的第"+i+"天"+calendar.get(Calendar.DATE) );
            //继续使用Calendar的目的是为了防止跨月份的情况出现
            calendar.add(Calendar.DATE, 1);
        }
	}


	public static void calcToday(String begin, String end, String now,
			GregorianCalendar calendar) {

		begin = now;
		end = now;
		System.out.println(" begin: " + begin);
		System.out.println(" end: " + end);
		System.out.println(" ---------------------- ");
	}

	public static void calcYesterday(String begin, String end, String now,
			GregorianCalendar calendar) {

		calendar.add(GregorianCalendar.DATE, -1);
		begin = new java.sql.Date(calendar.getTime().getTime()).toString();
		end = begin;
		System.out.println(" begin: " + begin);
		System.out.println(" end: " + end);
		System.out.println(" ---------------------- ");
	}

	public static void calcThisWeek(String begin, String end, String now,
			GregorianCalendar calendar) {
		end = now;
		int minus = calendar.get(GregorianCalendar.DAY_OF_WEEK) - 2;
		if (minus < 0) {
			System.out.println(" 本周还没有开始，请查询上周 ");
			System.out.println(" ---------------------- ");
		} else {

			calendar.add(GregorianCalendar.DATE, -minus);
			begin = new java.sql.Date(calendar.getTime().getTime()).toString();
			System.out.println(" begin: " + begin);
			System.out.println(" end: " + end);
			System.out.println(" ---------------------- ");
		}
	}

	public static void calcLastWeek(String begin, String end, String now,
			GregorianCalendar calendar) {
		int minus = calendar.get(GregorianCalendar.DAY_OF_WEEK) + 1;
		calendar.add(GregorianCalendar.DATE, -minus);
		end = new java.sql.Date(calendar.getTime().getTime()).toString();
		calendar.add(GregorianCalendar.DATE, -4);
		begin = new java.sql.Date(calendar.getTime().getTime()).toString();
		System.out.println(" begin: " + begin);
		System.out.println(" end: " + end);
		System.out.println(" ---------------------- ");
	}

	public static void calcThisMonth(String begin, String end, String now,
			GregorianCalendar calendar) {
		end = now;
		int dayOfMonth = calendar.get(GregorianCalendar.DATE);
		calendar.add(GregorianCalendar.DATE, -dayOfMonth + 1);
		begin = new java.sql.Date(calendar.getTime().getTime()).toString();
		System.out.println(" begin: " + begin);
		System.out.println(" end: " + end);
		System.out.println(" ---------------------- ");
	}

	public static void calcLastMonth(String begin, String end, String now,
			GregorianCalendar calendar) {

		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar
				.get(GregorianCalendar.MONTH), 1);
		calendar.add(GregorianCalendar.DATE, -1);
		end = new java.sql.Date(calendar.getTime().getTime()).toString();

		int month = calendar.get(GregorianCalendar.MONTH) + 1;
		begin = calendar.get(GregorianCalendar.YEAR) + " - " + month + " -01 ";

		System.out.println(" begin: " + begin);
		System.out.println(" end: " + end);
		System.out.println(" ---------------------- ");
	}
}
