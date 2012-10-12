package com.kurui.kums.base.database;

public class HtmlControlBean extends SelectDataBean {

	public HtmlControlBean() {
	}

	public String getNoFromDB(String tableName, String colName, String noType) {
		String strSQL = "";
		String strDate = "";
		String strFullZone = "";
		if (tableName == null || colName == null || noType.equals(""))
			return " ";
		try {
			setQuerySQL("select getDate()");
			executeQuery();
			String strTmp = getColValue(1, 1);
			strDate = (new StringBuilder(String.valueOf(noType.toUpperCase())))
					.append(strTmp.substring(2, 4)).append("-").append(
							strTmp.substring(5, 7)).toString();
		} catch (NullPointerException npe) {
			System.out.println("null pointer exception...");
			return "";
		} catch (IndexOutOfBoundsException ioofe) {
			System.out.println("index out of bounds exception...");
			return "";
		}
		strFullZone = (new StringBuilder("replace(str(max(substring(")).append(
				colName).append(",8,10))+1,3), ' ', '0') as no").toString();
		strSQL = (new StringBuilder("Select ")).append(strFullZone).append(
				" From ").append(tableName).toString();
		setQuerySQL(strSQL);
		executeQuery();
		String temp1 = getColValue(1, 1);
		if (temp1.compareTo("") == 0)
			strDate = (new StringBuilder(String.valueOf(strDate))).append("-")
					.append("001").toString();
		else
			strDate = (new StringBuilder(String.valueOf(strDate))).append("-")
					.append(getColValue(1, 1)).toString();
		return strDate;
	}

	public String getLabelFromData(String tableName, String DataColName,
			String DataValue, String LabelColName, String WhereCol,
			String WhereValue) {
		String strSQL = "";
		if (DataColName == null || DataValue == null || DataColName.equals("")) {
			return " ";
		} else {
			strSQL = (new StringBuilder(String.valueOf(strSQL))).append("(")
					.append(DataColName).append("='").append(DataValue).append(
							"') and (").append(WhereCol).append("='").append(
							WhereValue).append("')").toString();
			strSQL = (new StringBuilder("Select ")).append(LabelColName)
					.append(" From ").append(tableName).append(" Where ")
					.append(strSQL).toString();
			setQuerySQL(strSQL);
			executeQuery();
			return getColValue(1, 1);
		}
	}

	public String getLabelFromData(String tableName, String DataColName,
			String DataValue, String LabelColName) {
		String strSQL = "";
		if (DataColName == null || DataValue == null || DataColName.equals("")) {
			return " ";
		} else {
			strSQL = (new StringBuilder(String.valueOf(strSQL))).append("(")
					.append(DataColName).append("='").append(DataValue).append(
							"')").toString();
			strSQL = (new StringBuilder("Select ")).append(LabelColName)
					.append(" From ").append(tableName).append(" Where ")
					.append(strSQL).toString();
			setQuerySQL(strSQL);
			executeQuery();
			return getColValue(1, 1);
		}
	}

	public String getSelectOption(String defaultValue, int colData, int colLabel) {
		String lstr = "";
		boolean haveSetDefault = false;
		if (defaultValue == null)
			defaultValue = "";
		if (defaultValue.compareTo("") == 0)
			defaultValue = " ";
		for (int j = 1; j <= getRowCount(); j++)
			if (!haveSetDefault
					&& defaultValue.compareTo(getColValue(j, colData)) == 0)
				lstr = (new StringBuilder(String.valueOf(lstr))).append(
						"<option value=\"").append(getColValue(j, colData))
						.append("\" selected>")
						.append(getColValue(j, colLabel)).append("</option>")
						.toString();
			else
				lstr = (new StringBuilder(String.valueOf(lstr))).append(
						"<option value=\"").append(getColValue(j, colData))
						.append("\" >").append(getColValue(j, colLabel))
						.append("</option>").toString();

		return lstr;
	}

	public String getSelectOption(String defaultValue, int colData,
			int colLabel, boolean haveNull) {
		if (defaultValue == null)
			defaultValue = "";
		if (defaultValue.compareTo("") == 0)
			defaultValue = " ";
		if (haveNull) {
			if (defaultValue.compareTo(" ") == 0)
				return (new StringBuilder(
						"<option value=\" \" selected></option>")).append(
						getSelectOption(defaultValue, colData, colLabel))
						.toString();
			else
				return (new StringBuilder("<option value=\" \"></option>"))
						.append(
								getSelectOption(defaultValue, colData, colLabel))
						.toString();
		} else {
			return getSelectOption(defaultValue, colData, colLabel);
		}
	}

	public String getSelectOption(String defaultValue, String colData,
			String colLabel) {
		String lstr = "";
		boolean haveSetDefault = false;
		if (defaultValue == null)
			defaultValue = "";
		if (defaultValue.compareTo("") == 0)
			defaultValue = " ";
		for (int j = 1; j <= getRowCount(); j++)
			if (!haveSetDefault
					&& defaultValue.compareTo(getColValue(j, colData)) == 0)
				lstr = (new StringBuilder(String.valueOf(lstr))).append(
						"<option value=\"").append(getColValue(j, colData))
						.append("\" selected>")
						.append(getColValue(j, colLabel)).append("</option>")
						.toString();
			else
				lstr = (new StringBuilder(String.valueOf(lstr))).append(
						"<option value=\"").append(getColValue(j, colData))
						.append("\" >").append(getColValue(j, colLabel))
						.append("</option>").toString();

		return lstr;
	}

	public String getSelectOption(String defaultValue, String colData,
			String colLabel, boolean haveNull) {
		if (defaultValue == null)
			defaultValue = "";
		if (defaultValue.compareTo("") == 0)
			defaultValue = " ";
		if (haveNull) {
			if (defaultValue.compareTo(" ") == 0)
				return (new StringBuilder(
						"<option value=\" \" selected></option>")).append(
						getSelectOption(defaultValue, colData, colLabel))
						.toString();
			else
				return (new StringBuilder("<option value=\" \"></option>"))
						.append(
								getSelectOption(defaultValue, colData, colLabel))
						.toString();
		} else {
			return getSelectOption(defaultValue, colData, colLabel);
		}
	}

	public String getSelectOptionFromTable(String defaultValue,
			String tableName, String DataColName, String LabelColName) {
		String strSQL = "";
		strSQL = (new StringBuilder("Select ")).append(DataColName).append(",")
				.append(LabelColName).append(" From ").append(tableName)
				.toString();
		setQuerySQL(strSQL);
		executeQuery();
		return getSelectOption(defaultValue, 1, 2);
	}

	public String getSelectOptionFromTable(String defaultValue,
			String tableName, String DataColName, String LabelColName,
			boolean haveNull) {
		if (defaultValue == null)
			defaultValue = "";
		if (defaultValue.compareTo("") == 0)
			defaultValue = " ";
		if (haveNull) {
			if (defaultValue.compareTo(" ") == 0)
				return (new StringBuilder(
						"<option value=\" \" selected></option>")).append(
						getSelectOptionFromTable(defaultValue, tableName,
								DataColName, LabelColName)).toString();
			else
				return (new StringBuilder("<option value=\" \"></option>"))
						.append(
								getSelectOptionFromTable(defaultValue,
										tableName, DataColName, LabelColName))
						.toString();
		} else {
			return getSelectOptionFromTable(defaultValue, tableName,
					DataColName, LabelColName);
		}
	}

	public String getSelectOptionFromTable(String defaultValue,
			String tableName, String DataColName, String LabelColName,
			String WhereCol, String WhereData, boolean haveNull) {
		if (defaultValue == null)
			defaultValue = "";
		if (defaultValue.compareTo("") == 0)
			defaultValue = " ";
		if (haveNull) {
			if (defaultValue.compareTo(" ") == 0)
				return (new StringBuilder(
						"<option value=\" \" selected></option>"))
						.append(
								getSelectOptionFromTable(defaultValue,
										tableName, DataColName, LabelColName,
										WhereCol, WhereData)).toString();
			else
				return (new StringBuilder("<option value=\" \"></option>"))
						.append(
								getSelectOptionFromTable(defaultValue,
										tableName, DataColName, LabelColName,
										WhereCol, WhereData)).toString();
		} else {
			return getSelectOptionFromTable(defaultValue, tableName,
					DataColName, LabelColName, WhereCol, WhereData);
		}
	}

	public String getSelectOptionFromTable(String defaultValue,
			String tableName, String DataColName, String LabelColName,
			String WhereCol, String WhereData) {
		String strSQL = "";
		strSQL = (new StringBuilder("Select ")).append(DataColName).append(",")
				.append(LabelColName).append(" From ").append(tableName)
				.append(" where ").append(WhereCol).append("=").append(
						WhereData).toString();
		setQuerySQL(strSQL);
		executeQuery();
		return getSelectOption(defaultValue, 1, 2);
	}
}
