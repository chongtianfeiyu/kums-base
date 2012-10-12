package com.kurui.kums.base.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class FileExcelUtil {
	// private Log
	public static void main(String[] args) {
		// String filePath = "";
		// filePath = "D:" + File.separator
		// + Constant.PROJECT_PLATFORMREPORTS_PATH + File.separator
		// + "129162010120681.xls";
		// parseXLSFile(filePath, 0);
		// testJxlExample();

		ArrayList<Object> title = new ArrayList<Object>();
		title.add("titleA");
		title.add("titleB");
		title.add("titleC");

		ArrayList<ArrayList<Object>> lists = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list1 = new ArrayList<Object>();
		list1.add("a1");
		list1.add("a2");
		list1.add("a2");
		lists.add(list1);
		ArrayList<Object> list2 = new ArrayList<Object>();
		list2.add("b1");
		list2.add("b2");
		lists.add(list2);
		ArrayList<Object> list3 = new ArrayList<Object>();
		list3.add("b1");
		list3.add("c2");
		lists.add(list3);
		
		ArrayList<Object> list4 = new ArrayList<Object>();
		list4.add("d1");
		list4.add("d2");
		lists.add(list4);

		String sheetName = "管理费用报表";
		String filePath = "e:\\liveReport.xls";
		createXLSFile(title, lists, sheetName, filePath);
	}

	/**
	 * 创建XLS文件
	 * 
	 * @param String[]
	 *            title标题
	 * @param String
	 *            filePath 输出路径
	 */
	public static void createXLSFile(ArrayList<Object> title,
			ArrayList<ArrayList<Object>> lists, String sheetName,
			String filePath) {
		try {
			long start = System.currentTimeMillis();// 开始时间

			OutputStream os = new FileOutputStream(filePath);// 新建立一个jxl文件

			WritableWorkbook wwb = Workbook.createWorkbook(os); // 创建Excel工作薄
			WritableSheet sheet = wwb.createSheet(sheetName, 0);// 添加第一个工作表并设置第一个Sheet

			Label label;// 单元格

			// 标题
			for (int i = 0; i < title.size(); i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是y
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title.get(i).toString());
				// 将定义好的单元格添加到工作表中
				sheet.addCell(label);
			}

			String colTemp = "";
			for (int j = 0; j < lists.size(); j++) {
				List<Object> objList = lists.get(j);
				for (int k = 0; k < objList.size(); k++) {
					String content = (String) objList.get(k);

					Cell lastCell = sheet.getCell(k, j);// 获得上一行
					// System.out.println(lastCell.getColumn()+"--"+lastCell.getRow());//座标
					String lastContent = lastCell.getContents();

					if (lastContent.equals(content)) {
						sheet.mergeCells(k, j, k, j + 1);
						label = new Label(k, j + 1, "上下行合并");
						sheet.addCell(label);
					} else if (colTemp.equals(content)) {
						sheet.mergeCells(k - 1, j+1, k, j + 1);
						label = new Label(k, j + 1, "左右列合并");
						sheet.addCell(label);

					} else {
						label = new Label(k, j + 1, content);
						sheet.addCell(label);
					}
					colTemp = content;
				}
			}
			
			int totalRows=sheet.getRows();
			
			// 添加图片(注意此处jxl暂时只支持png格式的图片)
			// 0,1分别代表x,y 2,5代表宽和高占的单元格数
			sheet.addImage(new WritableImage(0, totalRows+1, 8, 12,
					new File("e:\\jfreechart-34384.png")));

			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();
			long end = System.currentTimeMillis();
			System.out.println("----完成该操作共用的时间是:" + (end - start) / 1000);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("---出现异常---" + e.getMessage());
		}
	}
	

	private static WritableCellFormat getAmountFormat() {
		jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##");
		jxl.write.WritableCellFormat wcf = new jxl.write.WritableCellFormat(nf);
		return wcf;
	}

	private static WritableCellFormat getNormalContentFormat() {
		WritableCellFormat wc = new WritableCellFormat();
		try {
			// 设置居中
			wc.setAlignment(Alignment.CENTRE);
			// 设置边框线
			wc.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 设置单元格的背景颜色
			// wc.setBackground(jxl.format.Colour.RED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wc;
	}

	private static WritableCellFormat getFontFormat() {
		jxl.write.WritableFont wfont = new jxl.write.WritableFont(WritableFont
				.createFont("隶书"), 20);
		WritableCellFormat font = new WritableCellFormat(wfont);
		return font;
	}

	/**
	 * 直接输出为指定CSV文件
	 */
	public static void exportCSVFile(String filePath,
			ArrayList<ArrayList<Object>> lists) {
		try {
			FileWriter fw = new FileWriter(filePath);

			for (int i = 0; i < lists.size(); i++) {
				ArrayList<Object> objList = lists.get(i);
				for (int j = 0; j < objList.size(); j++) {
					fw.write(objList.get(j) + ",");
				}
				fw.write("\r\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析Excel文件
	 */
	public static void parseXLSFile(String filePath, int sheetIndex) {
		try {
			Workbook book = Workbook.getWorkbook(new File(filePath));
			Sheet sheet = book.getSheet(sheetIndex);// getSheet(1)得到第1个sheet
			int rownum = sheet.getRows(); // 得到总行数
			for (int i = 1; i < rownum; i++) {
				String aa = sheet.getCell(1 - 1, i).getContents(); // 第i行的第1列
				String bb = sheet.getCell(4 - 1, i).getContents();// 第i行的第4列
				String cc = sheet.getCell(5 - 1, i).getContents();

				System.out.println("aa:" + aa);
				System.out.println("bb:" + bb);
				System.out.println("cc:" + cc);
			}
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// jxl暂时不提供修改已经存在的数据表,这里通过一个小办法来达到这个目的,不适合大型数据更新!
	// 这里是通过覆盖原文件来更新的.
	public static void updateExcel(String oldFilePath, String newFilePath) {
		try {
			Workbook rwb = Workbook.getWorkbook(new File(oldFilePath));
			WritableWorkbook wwb = Workbook.createWorkbook(
					new File(newFilePath), rwb);// copy
			WritableSheet ws = wwb.getSheet(0);
			WritableCell wc = ws.getWritableCell(0, 0);
			// 判断单元格的类型,做出相应的转换
			Label label = (Label) wc;
			label.setString("The value has been modified");
			wwb.write();
			wwb.close();
			rwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 后续考虑问题,比如Excel里面的图片以及其他数据类型的读取
	 */
	public static void readExcel(String filePath) {
		try {
			InputStream is = new FileInputStream(filePath);
			// 声名一个工作薄
			Workbook rwb = Workbook.getWorkbook(is);

			// 获得工作薄的个数
			// rwb.getNumberOfSheets();

			// 在Excel文档中，第一张工作表的缺省索引是0
			Sheet st = rwb.getSheet("Sheet1");

			// 通用的获取cell值的方式,getCell(int column, int row) 行和列
			int Rows = st.getRows();
			int Cols = st.getColumns();
			System.out.println("当前工作表的名字:" + st.getName());
			System.out.println("总行数:" + Rows);
			System.out.println("总列数:" + Cols);

			Cell c;
			for (int i = 0; i < Cols; ++i) {
				for (int j = 0; j < Rows; ++j) {
					// getCell(Col,Row)获得单元格的值
					System.out.print((st.getCell(i, j)).getContents() + "\t");
				}
				System.out.print("\n");
			}
			// 操作完成时，关闭对象，释放占用的内存空间
			rwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List parseCSV() {
		List result = null;
		return result;
	}

	// 行列的批量操作
	//	 
	// //获取所有的工作表
	// jxl.write.WritableSheet[] sheetList = wwb.getSheets();
	// //获取第1列所有的单元格
	// jxl.Cell[] cellc = sheet.getColumn(0);
	// //获取第1行所有的单元格
	// jxl.Cell[] cellr = sheet.getRow(0);
	// //获取第1行第1列的单元格
	// Cell c = sheet.getCell(0, 0);

	public static void testJxlExample() {
		// 准备设置excel工作表的标题
		String[] title = { "编号", "产品名称", "产品价格", "产品数量", "生产日期", "产地", "是否出口" };
		try {
			// 获得开始时间
			long start = System.currentTimeMillis();
			// 输出的excel的路径
			String filePath = "e:\\test.xls";
			// 创建Excel工作薄
			WritableWorkbook wwb;
			// 新建立一个jxl文件,即在C盘下生成test.xls
			OutputStream os = new FileOutputStream(filePath);
			wwb = Workbook.createWorkbook(os);
			// 添加第一个工作表并设置第一个Sheet的名字
			WritableSheet sheet = wwb.createSheet("产品清单", 0);
			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是y
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				// 将定义好的单元格添加到工作表中
				sheet.addCell(label);
			}
			// 下面是填充数据
			/*
			 * 保存数字到单元格，需要使用jxl.write.Number 必须使用其完整路径，否则会出现错误
			 */
			// 填充产品编号
			jxl.write.Number number = new jxl.write.Number(0, 1, 20071001);
			sheet.addCell(number);
			// 填充产品名称
			label = new Label(1, 1, "金鸽瓜子");
			sheet.addCell(label);
			/*
			 * 定义对于显示金额的公共格式 jxl会自动实现四舍五入 例如 2.456会被格式化为2.46,2.454会被格式化为2.45
			 */
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##");
			jxl.write.WritableCellFormat wcf = new jxl.write.WritableCellFormat(
					nf);
			// 填充产品价格
			jxl.write.Number nb = new jxl.write.Number(2, 1, 2.45, wcf);
			sheet.addCell(nb);
			// 填充产品数量
			jxl.write.Number numb = new jxl.write.Number(3, 1, 200);
			sheet.addCell(numb);
			/*
			 * 定义显示日期的公共格式 如:yyyy-MM-dd hh:mm
			 */
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String newdate = sdf.format(new Date());
			// 填充出产日期
			label = new Label(4, 1, newdate);
			sheet.addCell(label);
			// 填充产地
			label = new Label(5, 1, "陕西西安");
			sheet.addCell(label);
			/*
			 * 显示布尔值
			 */
			jxl.write.Boolean bool = new jxl.write.Boolean(6, 1, true);
			sheet.addCell(bool);
			/*
			 * 合并单元格 通过writablesheet.mergeCells(int x,int y,int m,int n);来实现的
			 * 表示将从第x+1列，y+1行到m+1列，n+1行合并
			 * 
			 */
			sheet.mergeCells(0, 3, 2, 3);
			label = new Label(0, 3, "合并了三个单元格");
			sheet.addCell(label);
			/*
			 * 
			 * 定义公共字体格式 通过获取一个字体的样式来作为模板 首先通过web.getSheet(0)获得第一个sheet
			 * 然后取得第一个sheet的第二列，第一行也就是"产品名称"的字体
			 */
			CellFormat cf = (CellFormat) wwb.getSheet(0).getCell(1, 0)
					.getCellFormat();
			WritableCellFormat wc = new WritableCellFormat();
			// 设置居中
			wc.setAlignment(Alignment.CENTRE);
			// 设置边框线
			wc.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 设置单元格的背景颜色
			wc.setBackground(jxl.format.Colour.RED);
			label = new Label(1, 5, "字体", wc);
			sheet.addCell(label);

			// 设置字体
			jxl.write.WritableFont wfont = new jxl.write.WritableFont(
					WritableFont.createFont("隶书"), 20);
			WritableCellFormat font = new WritableCellFormat(wfont);
			label = new Label(2, 6, "隶书", font);
			sheet.addCell(label);

			// 添加图片(注意此处jxl暂时只支持png格式的图片)
			// 0,1分别代表x,y 2,5代表宽和高占的单元格数
			sheet.addImage(new WritableImage(0, 1, 2, 5,
					new File("png\\cs.png")));

			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();
			long end = System.currentTimeMillis();
			System.out.println("----完成该操作共用的时间是:" + (end - start) / 1000);
		} catch (Exception e) {
			System.out.println("---出现异常---");
			e.printStackTrace();
		}
	}
}
