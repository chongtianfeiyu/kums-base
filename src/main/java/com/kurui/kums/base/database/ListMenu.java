package com.kurui.kums.base.database;

import com.kurui.kums.base.ValueLabelBean;

import java.util.ArrayList;
import java.util.Vector;

public class ListMenu extends ArrayList {
	private String menuSQL;
	private String text[];
	private String value[];
	private Vector vctValue;
	private Vector vctText;

	public ListMenu() {
		menuSQL = "";
		vctValue = new Vector();
		vctText = new Vector();
	}

	public ListMenu(String menuListSQL) {
		menuSQL = "";
		vctValue = new Vector();
		vctText = new Vector();
		getDataFromDB(menuListSQL);
	}

	public ListMenu(String menuListSQL, boolean f) {
		menuSQL = "";
		vctValue = new Vector();
		vctText = new Vector();
		getDataFromDB(menuListSQL, f);
	}

	public ListMenu(int pageCount) {
		menuSQL = "";
		vctValue = new Vector();
		vctText = new Vector();
		getDataFromDB(pageCount);
	}

	public ListMenu(String value[], String label[]) {
		menuSQL = "";
		vctValue = new Vector();
		vctText = new Vector();
		getDataFromDB(value, label);
	}

	public ListMenu(Vector vctValue, Vector vctText) {
		menuSQL = "";
		this.vctValue = new Vector();
		this.vctText = new Vector();
		getDataFromDB(vctValue, vctText);
	}

	public void removeChild(int i) {
		super.remove(i);
		Object temp = vctValue.remove(i);
		temp = vctText.remove(i);
	}

	private void getDataFromDB(String sql) {
		SelectDataBean sdb = new SelectDataBean();
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		add(new ValueLabelBean("", "\u0461\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD..."));
		int rowcount = sdb.getRowCount();
		value = new String[rowcount];
		text = new String[rowcount];
		String str = new String();
		for (int i = 1; i < rowcount + 1; i++) {
			value[i - 1] = sdb.getColValue(i, 1);
			text[i - 1] = sdb.getColValue(i, 2);
			vctValue.add(sdb.getColValue(i, 1));
			str = sdb.getColValue(i, 2);
			vctText.add(str);
			add(new ValueLabelBean(value[i - 1], text[i - 1]));
		}

	}

	private void getDataFromDB(String sql, boolean f) {
		SelectDataBean sdb = new SelectDataBean();
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		if (f)
			add(new ValueLabelBean("", "\u0461\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD..."));
		int rowcount = sdb.getRowCount();
		value = new String[rowcount];
		text = new String[rowcount];
		String str = new String();
		for (int i = 1; i < rowcount + 1; i++) {
			value[i - 1] = sdb.getColValue(i, 1);
			text[i - 1] = sdb.getColValue(i, 2);
			vctValue.add(sdb.getColValue(i, 1));
			str = sdb.getColValue(i, 2);
			vctText.add(str);
			add(new ValueLabelBean(value[i - 1], text[i - 1]));
		}

	}

	private void getDataFromDB(String value[], String label[]) {
		this.value = value;
		text = label;
		for (int i = 0; i < label.length; i++)
			add(new ValueLabelBean(value[i], label[i]));

	}

	private void getDataFromDB(Vector vctValue, Vector vctText) {
		for (int i = 0; i < vctValue.size(); i++)
			add(new ValueLabelBean((String) vctValue.get(i), (String) vctText
					.get(i)));

		setVctValue(vctValue);
		setVctText(vctText);
	}

	public void getDataFromDB(int pageCount) {
		String num = "";
		for (int i = 0; i < pageCount; i++) {
			num = Integer.toString(i);
			add(new ValueLabelBean(num, num));
		}

	}

	public String[] getValue() {
		return value;
	}

	public String[] getText() {
		return text;
	}

	public Vector getVctValue() {
		return vctValue;
	}

	public void setVctValue(Vector vctValue) {
		this.vctValue = vctValue;
	}

	public Vector getVctText() {
		return vctText;
	}

	public void setVctText(Vector vctText) {
		this.vctText = vctText;
	}

	public static void main(String arg[]) {
		ListMenu lm = new ListMenu();
		System.out.println((new StringBuilder("length=")).append(lm.size())
				.toString());
	}
}
