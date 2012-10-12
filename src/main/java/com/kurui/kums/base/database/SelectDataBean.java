package com.kurui.kums.base.database;

import java.sql.*;
import java.util.Vector;

public class SelectDataBean {

	private static final boolean blnDebug = true;
	private int intRowCount;
	private int intColCount;
	private String strSQL;
	private Vector vctColName;
	private Vector vctColValue;
	private Vector vctColType;
	private Connection dbConn;
	private Statement stmt;
	int line;

	public SelectDataBean() {
		intRowCount = 0;
		intColCount = 0;
		strSQL = "";
		vctColName = new Vector();
		vctColValue = new Vector();
		vctColType = new Vector();
		stmt = null;
		line = 0;
	}

	private void init() {
		try {
			int count = 0;
			long a = System.currentTimeMillis();
			dbConn = DBCHPool.getConnection();
			long b = System.currentTimeMillis();
			for (; stmt == null && count < 5; count++) {
				if (count > 0)
					wait(20L);
				stmt = dbConn.createStatement(1003, 1007);
			}

		} catch (Exception exception) {
		}
	}

	private void initUpdate() {
		try {
			int count = 0;
			dbConn = DBCHPool.getConnection();
			for (; stmt == null && count < 5; count++) {
				if (count > 0)
					wait(20L);
				stmt = dbConn.createStatement(1003, 1008);
			}

		} catch (Exception exception) {
		}
	}

	public Connection getConnection() {
		try {
			Connection conn = DBCHPool.getConnection();
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int callProcedure(String strProcedure) {
		CallableStatement cstmt = null;
		try {
			dbConn = DBCHPool.getConnection();
			cstmt = dbConn.prepareCall(strProcedure);
			cstmt.execute();
			cstmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return -1;
		}
		System.out.println("the call procedure seccessfully!");
		return 0;
	}

	public void executeUpdateSQL(String paramSQL) {
		try {
			if (stmt == null)
				init();
			stmt.executeUpdate(paramSQL);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void executeQuery() {
		long a = System.currentTimeMillis();
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		Object tmpValueObj = null;
		Object object = null;
		try {
			line++;
			if (stmt == null || line > 100) {
				stmt = null;
				init();
				line = 0;
			}
			rs = stmt.executeQuery(sqlFilter(strSQL));
			rsmd = rs.getMetaData();
			intColCount = rsmd.getColumnCount();
			vctColName.setSize(intColCount);
			vctColType.setSize(intColCount);
			for (int i = 1; i <= intColCount; i++) {
				String strTmp = "";
				strTmp = rsmd.getColumnName(i);
				if (strTmp == null)
					strTmp = "";
				vctColName.setElementAt(strTmp, i - 1);
				strTmp = rsmd.getColumnTypeName(i);
				if (strTmp == null)
					strTmp = "";
				vctColType.setElementAt(strTmp, i - 1);
			}

			int tmpCount = 0;
			Vector vctColValueForRow;
			for (; rs.next(); vctColValue.add(vctColValueForRow)) {
				tmpCount++;
				vctColValueForRow = null;
				vctColValueForRow = new Vector();
				vctColValueForRow.setSize(intColCount);
				for (int j = 1; j <= intColCount; j++) {
					object = rs.getObject(j);
					if (object == null)
						tmpValueObj = "";
					else
						tmpValueObj = object.toString();
					vctColValueForRow.setElementAt(tmpValueObj, j - 1);
				}

			}

			vctColValue.trimToSize();
			intRowCount = tmpCount;
		} catch (Exception ex) {
			ex.printStackTrace();
			resetBean();
		}
		try {
			rs.close();
		} catch (Exception exception) {
		}
	}

	public void _executeQuery() throws DataBaseException {
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		String tmpValueObj = null;
		Object object = null;
		try {
			line++;
			if (stmt == null || line > 100) {
				stmt = null;
				init();
				line = 0;
			}
			rs = stmt.executeQuery(sqlFilter(strSQL));
			rsmd = rs.getMetaData();
			intColCount = rsmd.getColumnCount();
			vctColName.setSize(intColCount);
			vctColType.setSize(intColCount);
			for (int i = 1; i <= intColCount; i++) {
				String strTmp = "";
				strTmp = rsmd.getColumnName(i);
				if (strTmp == null)
					strTmp = "";
				vctColName.setElementAt(strTmp, i - 1);
				strTmp = rsmd.getColumnTypeName(i);
				if (strTmp == null)
					strTmp = "";
				vctColType.setElementAt(strTmp, i - 1);
			}

			int tmpCount = 0;
			Vector vctColValueForRow;
			for (; rs.next(); vctColValue.add(vctColValueForRow)) {
				tmpCount++;
				vctColValueForRow = null;
				vctColValueForRow = new Vector();
				vctColValueForRow.setSize(intColCount);
				for (int j = 1; j <= intColCount; j++) {
					object = rs.getObject(j);
					if (object == null)
						tmpValueObj = "";
					else
						tmpValueObj = object.toString();
					vctColValueForRow.setElementAt(tmpValueObj, j - 1);
				}

			}

			vctColValue.trimToSize();
			intRowCount = tmpCount;
		} catch (Exception ex) {
			ex.printStackTrace();
			resetBean();
		}
		try {
			rs.close();
		} catch (Exception exception) {
		}
	}

	public int getColCount() {
		return intColCount;
	}

	public String getColValue(int intRow, int intCol) {
		String strRet = " ";
		Vector tmpVector = null;
		if (vctColValue.size() != intRowCount)
			return "";
		if (vctColName.size() != intColCount)
			return "";
		if (intRow < 1 || intRow > intRowCount)
			return "";
		if (intCol < 1 || intCol > intColCount)
			return "";
		tmpVector = (Vector) vctColValue.get(intRow - 1);
		if (tmpVector.size() != intColCount)
			return "";
		if (tmpVector.size() < intCol)
			return "";
		strRet = (String) tmpVector.get(intCol - 1);
		if (strRet == null || strRet.compareTo("") == 0)
			strRet = "";
		if (strRet.equals("true"))
			strRet = "1";
		if (strRet.equals("false"))
			strRet = "0";
		return strRet;
	}

	public String getColValue(int intRow, String strCol) {
		String strRet = " ";
		int intColPos = 0;
		if (vctColValue.size() != intRowCount)
			return "";
		if (vctColName.size() != intColCount)
			return "";
		if (intRow < 1 || intRow > intRowCount)
			return "";
		if (strCol == null || strCol.trim().compareToIgnoreCase("") == 0)
			return "";
		for (int i = 0; i < vctColName.size(); i++) {
			String strTmp = "";
			strTmp = (String) vctColName.get(i);
			if (strTmp == null)
				strTmp = "";
			if (strTmp.compareToIgnoreCase(strCol) != 0)
				continue;
			intColPos = i + 1;
			break;
		}

		if (intColPos == 0)
			return "";
		else
			return getColValue(intRow, intColPos);
	}

	public int getRowCount() {
		return intRowCount;
	}

	private void resetBean() {
		intRowCount = 0;
		intColCount = 0;
		vctColName = null;
		vctColName = new Vector();
		vctColValue = null;
		vctColValue = new Vector();
		vctColType = null;
		vctColType = new Vector();
	}

	public void setQuerySQL(String paramSQL) {
		resetBean();
		strSQL = paramSQL;
		if (strSQL == null)
			strSQL = "";
	}



	private String sqlFilter(String sql) {
		String filter[] = { "exec", "drop" };
		int p = 0;
		for (int i = 0; i < filter.length; i++) {
			p = sql.indexOf(filter[i].toLowerCase());
			if (p > 0)
				sql = sql.substring(0, p);
			p = sql.indexOf(filter[i].toUpperCase());
			if (p > 0)
				sql = sql.substring(0, p);
		}

		return sql;
	}

	protected void finalize() throws Throwable {
		super.finalize();
		close();
	}

	public void close() {
		if (dbConn != null)
			try {
				if (!dbConn.isClosed())
					dbConn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}

	public static void main(String arg[]) {
		try {
			String str = "test";
			SelectDataBean sdb = new SelectDataBean();
			sdb.setQuerySQL("select * from sys_user");
			sdb.executeQuery();
			for (int i = 1; i < sdb.getRowCount() + 1; i++)
				System.out.println(sdb.getColValue(i, "user_name"));

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
