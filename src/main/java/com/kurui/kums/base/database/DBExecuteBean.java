package com.kurui.kums.base.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBExecuteBean {
	private Connection dbConn;
	private Statement stmt;
	private String strStatusText;
	private int intStatusCode;
	private int sqlErrorCode;
	private int intInsertID;

	public DBExecuteBean() {
		stmt = null;
		strStatusText = "";
		intStatusCode = -1;
		intInsertID = -1;
		init();
	}

	public int executeUpdateSQL(String paramSQL) {
		int intRet = -1;
		strStatusText = (new StringBuilder("sql:")).append(paramSQL).toString();
		if (stmt == null){
			init();
		}			
		
//		if (paramSQL.trim().substring(0, 6).equals("insert")){
//			return -1;
//		}			
		
		try {
			intRet = stmt.executeUpdate(paramSQL);
//			commitTransaction();
			intStatusCode = intRet;
		} catch (Exception ex) {
			ex.printStackTrace();
			intRet = -1;
			strStatusText = (new StringBuilder("ExecuteUpdateSQL:")).append(
					ex.toString()).toString();
			System.out.println(strStatusText);
			System.out.println((new StringBuilder("error sql :")).append(
					paramSQL).toString());
			
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println((new StringBuilder("stmt.close():")).append(
						ex.getMessage()).toString());
			}
			
			if (ex instanceof SQLException) {
				SQLException sqle = (SQLException) ex;
				sqlErrorCode = sqle.getErrorCode();
			} else {
				sqlErrorCode = 0;
			}
		}
		intStatusCode = intRet;
		return intRet;
	}

	public int getSqlErrorCode() {
		return sqlErrorCode;
	}

	public int getStatusCode() {
		return intStatusCode;
	}

	public String getStatusText() {
		return strStatusText;
	}

	public void beginTransaction() throws SQLException {
		if (dbConn.getAutoCommit()) {
			dbConn.setAutoCommit(false);
			dbConn.setTransactionIsolation(8);
		}
	}

	public void commitTransaction() throws SQLException {
		dbConn.commit();
		closeConnection();
	}

	public void rollbackTransaction() {
		try {
			stmt.close();
			dbConn.rollback();
			closeConnection();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println((new StringBuilder(
					"conn't rollback transaction:")).append(ex.getMessage())
					.toString());
		}
	}

	public void setAutoCommit(boolean is_autocommit) throws SQLException {
		dbConn.setAutoCommit(is_autocommit);
	}

	void setSqlErrorCode(int newSqlErrorCode) {
		sqlErrorCode = newSqlErrorCode;
	}

	public int getIntInsertID() {
		return intInsertID;
	}

	public void setIntInsertID(int intInsertID) {
		this.intInsertID = intInsertID;
	}

	public void init() {
		int count = 0;
		try {
			while (stmt == null && count <= 3) {
//				if (count > 0)
//					wait(500L);
				dbConn = DBCHPool.getConnection();
				count++;
				if(dbConn!=null){
					dbConn.setAutoCommit(true);
					stmt = dbConn.createStatement(1003, 1008);
				}else{
					System.out.println("getConnection is null");
				}				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println((new StringBuilder("init()异常")).append(
					ex.getMessage()).toString());
		}
	}

	public int callProcedure(String strProcedure) {
		CallableStatement cstmt = null;
		try {
			dbConn = DBCHPool.getConnection();
			dbConn.setAutoCommit(true);
			cstmt = dbConn.prepareCall(strProcedure);
			cstmt.execute();
			cstmt.close();
		} catch (SQLException ex) {
			System.out.println((new StringBuilder("the call procedure("))
					.append(strProcedure).append(") error!").append(
							ex.getMessage()).toString());
			return -1;
		}
		System.out.println("the call procedure seccessfully!");
		return 0;
	}

	private void closeConnection() {
		try {
			stmt.close();
			dbConn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("closeConnection()异常：" + ex.getMessage());
			stmt = null;
			dbConn = null;
		}
	}
}
