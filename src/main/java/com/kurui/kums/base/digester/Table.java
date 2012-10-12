package com.kurui.kums.base.digester;

import java.util.Vector;
import com.kurui.kums.base.digester.Field;

public class Table {

	private String title;
	private String rootTagName;
	private String rsTagName;
	private String rsCount;
	private String tableID;
	private String tableName;
	private String fieldName;
	private String relatedTableName;
	private String relatedFieldName;
	private String whereFieldName;
	private String whereFieldValue;
	private String orderByFieldName;
	private String orderBy;
	private String outFileName;
	private Vector fields;

	public Table() {
		title = "";
		rootTagName = "";
		rsTagName = "";
		rsCount = "";
		tableID = "";
		tableName = "";
		fieldName = "";
		relatedTableName = "";
		relatedFieldName = "";
		whereFieldName = "";
		whereFieldValue = "";
		orderByFieldName = "";
		orderBy = "";
		outFileName = "";
		fields = new Vector();
	}

	public void addField(Field f) {
		fields.add(f);
	}

	public Field get(int i) {
		return (Field) fields.get(i);
	}

	public String getTitle() {
		return title;
	}

	public Vector getFields() {
		return fields;
	}

	public int getFieldSize() {
		return fields.size();
	}

	public String getRootTagName() {
		return rootTagName;
	}

	public String getRsTagName() {
		return rsTagName;
	}

	public String getTableID() {
		return tableID;
	}

	public void setTableID(String tableID) {
		this.tableID = tableID;
	}

	public String getTableName() {
		return tableName;
	}

	public String getWhereFieldName() {
		return whereFieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getRelatedFieldName() {
		return relatedFieldName;
	}

	public void setRelatedFieldName(String relatedFieldName) {
		this.relatedFieldName = relatedFieldName;
	}

	public String getRelatedTableName() {
		return relatedTableName;
	}

	public void setRelatedTableName(String relatedTableName) {
		this.relatedTableName = relatedTableName;
	}

	public String getWhereFieldValue() {
		return whereFieldValue;
	}

	public String getOutFileName() {
		return outFileName;
	}

	public void setOutFileName(String outFileName) {
		this.outFileName = outFileName;
	}

	public void setFields(Vector fields) {
		this.fields = fields;
	}

	public void setRootTagName(String rootTagName) {
		this.rootTagName = rootTagName;
	}

	public void setRsTagName(String rsTagName) {
		this.rsTagName = rsTagName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setWhereFieldName(String whereFieldName) {
		this.whereFieldName = whereFieldName;
	}

	public void setWhereFieldValue(String whereFieldValue) {
		this.whereFieldValue = whereFieldValue;
	}

	public String getRsCount() {
		return rsCount;
	}

	public void setRsCount(String rsCount) {
		this.rsCount = rsCount;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderByFieldName() {
		return orderByFieldName;
	}

	public void setOrderByFieldName(String orderByFieldName) {
		this.orderByFieldName = orderByFieldName;
	}
}
