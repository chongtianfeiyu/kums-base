package com.kurui.kums.base.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

public class DBCHPool {

	private static BasicDataSource dataSource = null;
	private static String resourceBundle = "";

	public DBCHPool() {
	}

	public void setDataSource(BasicDataSource d) {
		dataSource = d;
	}

	public void setResourceBundle(String r) {
		resourceBundle = r;
	}

	public static void init() {
		if (dataSource != null) {
			try {
				dataSource.close();
			} catch (Exception exception) {
			}
			dataSource = null;
		}
		try {
			Locale locale = Locale.getDefault();
			ResourceBundle my = ResourceBundle
					.getBundle(resourceBundle, locale);
			Properties p = new Properties();
			p.setProperty("driverClassName", my.getString("driverClassName"));
			p.setProperty("url", my.getString("url"));
			p.setProperty("password", my.getString("password"));
			p.setProperty("username", my.getString("username"));
			p.setProperty("maxActive", my.getString("maxActive"));
			p.setProperty("maxIdle", my.getString("maxIdle"));
			p.setProperty("maxWait", my.getString("maxWait"));
			p.setProperty("removeAbandoned", my.getString("removeAbandoned"));
			p.setProperty("removeAbandonedTimeout", my
					.getString("removeAbandonedTimeout"));
			p.setProperty("testOnBorrow", my.getString("testOnBorrow"));
			p.setProperty("logAbandoned", my.getString("logAbandoned"));
			dataSource = (BasicDataSource) BasicDataSourceFactory
					.createDataSource(p);
			
			if(dataSource!=null){
				System.out.println("DBCHPool init dataSource success ");
			}else{
				System.out.println("DBCHPool init dataSource failed ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println((new StringBuilder("dataSource create error:="))
					.append(e.getMessage()).toString());
		}
	}

	public static synchronized Connection getConnection() throws SQLException {
		Connection conn = null;
		if (dataSource != null) {
			try {
				conn = dataSource.getConnection();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}else{
			System.out.println("dataSource is null");
		}
		return conn;
	}

	public static void main(String args[]) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String user = "kums";
			String pass = "kurui001";
			String url = "jdbc:oracle:thin:@192.168.150.1:1521:orcl";
			Connection conn = DriverManager.getConnection(url, user, pass);
			System.out.println("conn:" + conn);
			Statement stmt = conn.createStatement();
			System.out.println("stmt:" + stmt);

			for (ResultSet rs = stmt.executeQuery("select id,name from agent"); rs
					.next(); System.out.println(rs.getString(1)))
				;

			// ResultSet rs = stmt.executeQuery("select * from sys_user ");
			// System.out.println(rs.next());

			conn.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
