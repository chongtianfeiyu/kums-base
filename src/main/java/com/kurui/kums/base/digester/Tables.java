package com.kurui.kums.base.digester;

import java.util.Vector;

public class Tables {
	private Vector tables;
	private int size;

	public Tables() {
		tables = new Vector();
		size = 0;
	}

	public void addTable(Table a) {
		tables.add(a);
	}

	public Table get(int i) {
		return (Table) tables.get(i);
	}

	public Table getTable(String tid) {
		if (tid == null || tid.equals(""))
			return null;
		for (int i = 0; i < tables.size(); i++) {
			Table t = (Table) tables.get(i);
			if (t.getTableID().equals(tid))
				return t;
		}

		return null;
	}

	public int getSize() {
		return tables.size();
	}
}
