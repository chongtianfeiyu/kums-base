package com.kurui.kums.base.database;

import java.util.ArrayList;

public class Hql {

	private ArrayList parameters;
	private String sql;

	public Hql() {
		parameters = new ArrayList();
		sql = "";
	}

	public Hql(String sql) {
		parameters = new ArrayList();
		this.sql = "";
		this.sql = sql;
	}

	public void add(String sql) {
		this.sql = (new StringBuilder(String.valueOf(this.sql))).append(" ")
				.append(sql).toString();
	}

	public void addHql(Hql hql) {
		add(hql.toString());
		for (int i = 0; i < hql.parameters.size(); i++) {
			parameters.add(hql.parameters.get(i));
		}
	}

	public void clear() {
		sql = "";
		parameters.clear();
	}

	public void addParamter(Object obj) {
		if (parameters == null)
			parameters = new ArrayList();
		parameters.add(obj);
	}

	public void addParamter(int index, Object obj) {
		if (parameters == null)
			parameters = new ArrayList();
		parameters.add(index, obj);
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String toString() {
		return sql;
	}

	public ArrayList getParameters() {
		return parameters;
	}

	public void setParameters(ArrayList parameters) {
		this.parameters = parameters;
	}
}
