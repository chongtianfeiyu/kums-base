package com.kurui.kums.base.digester;

public class Field {

	private String fieldName;
	private String fieldTagName;
	private boolean cityID;
	private boolean cityCode;
	private boolean agentName;

	public Field() {
		fieldName = "";
		fieldTagName = "";
		cityID = false;
		cityCode = false;
		agentName = false;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldTagName() {
		return fieldTagName;
	}

	public void setFieldTagName(String fieldTagName) {
		this.fieldTagName = fieldTagName;
	}

	public boolean isCityID() {
		return cityID;
	}

	public void setCityID(boolean cityID) {
		this.cityID = cityID;
	}

	public boolean isCityCode() {
		return cityCode;
	}

	public void setCityCode(boolean cityCode) {
		this.cityCode = cityCode;
	}

	public boolean isAgentName() {
		return agentName;
	}

	public void setAgentName(boolean agentName) {
		this.agentName = agentName;
	}
}
