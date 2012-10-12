package com.kurui.kums.base.database;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;


public class DBExecuteBatch {

	Connection dbConn;
	Statement stmt;
	boolean success;
	String message;
	private ArrayList sb;
	
	public static void main(String[] args) {
		String sql = "insert into role(role_id,role_name,role_key,role_description,role_system,role_status,role_type) values(seq_role.nextval,'出纳','null','',1,1,1)";
		DBExecuteBatch db=new DBExecuteBatch();
		db.append(sql);
		db.executeBatch();
		
	}

	public DBExecuteBatch() {
		stmt = null;
		success = true;
		message = "";
		sb = new ArrayList();
	}

	public boolean isSuccess() {
		return success;
	}

	public void append(String sql) {
		try {
			sb.add(sql);
		} catch (Exception ex) {
			success = false;
			message = (new StringBuilder("sql:")).append(ex.getMessage())
					.toString();
			System.out.println(ex.getMessage());
		}
	}

	public void append(ArrayList sqls) {
		try {
			sb = sqls;
		} catch (Exception ex) {
			success = false;
			message = (new StringBuilder("sql:")).append(ex.getMessage())
					.toString();
			System.out.println((new StringBuilder("sql:")).append(
					ex.getMessage()).toString());
		}
	}

	public void executeBatch() {
		int statusCode[];
		init();
		statusCode = new int[0];
		try {
			int count = 0;
			for (int i = 0; i < sb.size(); i++) {
				count++;
				stmt.addBatch((new StringBuilder()).append(sb.get(i)).append(
						" ").toString());
				if (count > 30) {
					count = 0;
					statusCode = stmt.executeBatch();
					stmt.clearBatch();
				}
			}

			System.out.println((new StringBuilder("sql:")).append(sb)
					.toString());
			if (count > 0)
				statusCode = stmt.executeBatch();
			message = (new StringBuilder(String.valueOf(statusCode.length)))
					.toString();
			dbConn.commit();
			sb.clear();
			success = true;
			stmt.close();
			dbConn.close();
		} catch (Exception ex) {
			success = false;
			message = ex.getMessage();
			try {
				dbConn.rollback();
				stmt.close();
				dbConn.close();
			} catch (Exception e) {
				dbConn = null;
			}
			println(statusCode);
		}
		try {
			dbConn.close();
		} catch (Exception e) {
			dbConn = null;
		}

		try {
			dbConn.close();
		} catch (Exception e) {
			dbConn = null;
		}

		try {
			dbConn.close();
		} catch (Exception e) {
			dbConn = null;
		}
	}

	private void init() {
		int count = 0;
		try {
			while (stmt == null && count <= 3) {
				if (count > 0)
					wait(500L);
				dbConn = DBCHPool.getConnection();
				dbConn.setAutoCommit(false);
				stmt = dbConn.createStatement(1003, 1008);
			}
		} catch (Exception ex) {
			success = false;
			System.out.println((new StringBuilder("dbe init error:")).append(
					ex.getMessage()).toString());
		}
	}

	private void println(int statusCode[]) {
		for (int i = 0; i < statusCode.length; i++)
			System.out.println((new StringBuilder("**statusCode[")).append(i)
					.append("]=").append(statusCode[i]).toString());

	}

	public String getMessage() {
		return message;
	}
	

}
