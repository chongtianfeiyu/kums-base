package com.kurui.kums.base.database.app;

import com.kurui.kums.base.Constant;
import com.kurui.kums.base.Event;
import com.kurui.kums.base.database.SelectDataBean;

public class CheckTable {

	private SelectDataBean sdb;

	public CheckTable() {
		sdb = new SelectDataBean();
	}

	public int getDataNum(String strTable, String strField, String data) {
		String sql = (new StringBuilder("select ")).append(strField).append(
				" from ").append(strTable).append(" where ").append(strField)
				.append("='").append(data).append("'").toString();
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		return sdb.getRowCount();
	}

	public boolean ifHasCustomerNo(String customerID, String field,
			String fieldValue) {
		String sql = (new StringBuilder("select ")).append(field).append(
				" from  st_customer where ").append(field).append("='").append(
				fieldValue).append("'").toString();
		sql = (new StringBuilder(String.valueOf(sql))).append(
				" and customer_id!=").append(customerID).toString();
		if (customerID.equals(""))
			sql = "select customer_no from  st_customer";
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		System.out.println((new StringBuilder("sql=")).append(sql).toString());
		System.out.println((new StringBuilder("rowcount=")).append(
				sdb.getRowCount()).toString());
		return sdb.getRowCount() > 0;
		// exception
		// System.out.println((new
		// StringBuilder("ifHasCustomerNo()")).append(ex.getMessage()).toString());
		// return false;
	}

	public boolean ifHasChildCustomer(String customerID) {
		String sql = (new StringBuilder(
				"select customer_id from  st_customer where customer_parent_id="))
				.append(customerID).toString();
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		System.out.println((new StringBuilder("sql=")).append(sql).toString());
		return sdb.getRowCount() > 0;
		// Exception ex;
		// ex;
		// System.out.println((new
		// StringBuilder("ifHasChildCustomer():")).append(ex.getMessage()).toString());
		// return false;
	}

	public Event getEventByID(String eventTable, String fieldID,
			String relationID, int type) {
		Event tv = new Event();
		String sql = (new StringBuilder(
				"select top 1 event_user_id, event_type,event_note from "))
				.append(eventTable).append(" where ").append(fieldID).append(
						"=").append(relationID).append(" and event_type=")
				.append(type).append(" order by event_date desc").toString();
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		tv.setEventUserID(Constant.toInt(sdb.getColValue(1, "event_user_id")));
		tv.setEventType(sdb.getColValue(1, "event_type"));
		tv.setEventNote(sdb.getColValue(1, "event_note"));
		return tv;
	}

	public String getLockUserIDByID(String eventTable, String fieldID,
			String relationID) {
		String sql = (new StringBuilder("select top 1 event_user_id from "))
				.append(eventTable).append(" where ").append(fieldID).append(
						"=").append(relationID).append(
						" and event_type=11 order by event_date desc")
				.toString();
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		if (sdb.getRowCount() == 1)
			return sdb.getColValue(1, "event_user_id");
		else
			return "0";
	}

	public String getDepartNameByID(String departID) {
		if (departID.equals(""))
			departID = "0";
		String sql = (new StringBuilder(
				"select depart_name from st_agent_class where depart_id="))
				.append(departID).toString();
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		return sdb.getColValue(1, "depart_name");
	}

	public boolean ifHasData(String strTable, String strField) {
		String sql = (new StringBuilder("select ")).append(strField).append(
				" from ").append(strTable).toString();
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		return sdb.getRowCount() > 0;
	}

	public boolean ifHasData(String strTable, String strField, String data) {
		String sql = (new StringBuilder("select ")).append(strField).append(
				" from ").append(strTable).append(" where ").append(strField)
				.append("='").append(data).append("'").toString();
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		return sdb.getRowCount() > 0;
	}

	public int getAccountOfAgent(String agentID) {
		String sql;
		int temp = 0;
		sql = (new StringBuilder(
				"select sum(account_sum) from st_agent_account group by agent_id having agent_id="))
				.append(agentID).toString();
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		sql = sdb.getColValue(1, 1);
		if (sql.equals(""))
			return 0;
		try {
			temp = Integer.parseInt(sql);
		} catch (Exception e) {
			return 0;
		}
		return temp;
	}

	public String getAgentClassByID(String agentID) {
		String sql = (new StringBuilder(
				"select agent_class from st_agent where agent_id=")).append(
				agentID).toString();
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		return sdb.getColValue(1, 1);
	}

	public String getTicketStatus(String bookID) {
		String sql = (new StringBuilder(
				"select book_status from st_book_info where book_id=")).append(
				bookID).toString();
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		sql = sdb.getColValue(1, 1);
		if (sql.equals(""))
			return "";
		else
			return sql;
	}

	public String getReserveID(String table, String fieldName, String fieldValue) {
		String fieldIDName = "reserve_id";
		if (table.equals("st_book_info"))
			fieldIDName = "book_id";
		String sql = (new StringBuilder("select top 1 ")).append(fieldIDName)
				.append(" from ").append(table).append(" where ").append(
						fieldName).append("='").append(fieldValue).append(
						"' order by ").append(fieldName).append(" desc")
				.toString();
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		if (sdb.getRowCount() == 1)
			return sdb.getColValue(1, fieldIDName);
		else
			return "0";
	}

	public boolean isHasOrder(String agentID) {
		if (Constant.toInt(agentID) < 1)
			return false;
		StringBuffer sql = new StringBuffer();
		sql
				.append(" select top 1 place_id as book_id  from st_ticket_place where ");
		sql.append((new StringBuilder(" agent_id =")).append(agentID)
				.toString());
		sql.append(" union");
		sql.append(" select top 1 book_id  from st_book_info where ");
		sql.append((new StringBuilder(" city_agent_id =")).append(agentID)
				.toString());
		sql.append((new StringBuilder(" or reserve_agent_id ="))
				.append(agentID).toString());
		sql.append((new StringBuilder(" or locate_agent_id =")).append(agentID)
				.toString());
		sql.append(" union");
		sql
				.append(" select top 1 book_id  from st_book_event where event_user_id in ");
		sql.append((new StringBuilder(
				" (select user_id from st_user where agent_id=")).append(
				agentID).append(")").toString());
		sql.append(" union");
		sql
				.append(" select top 1 reserve_id as  book_id  from st_newhotel_reserve where ");
		sql.append((new StringBuilder(" city_agent_id =")).append(agentID)
				.toString());
		sql.append(" or user_id in ");
		sql.append((new StringBuilder(
				" (select user_id from st_user where agent_id=")).append(
				agentID).append(")").toString());
		sql.append(" union");
		sql
				.append(" select top 1 reserve_id as  book_id  from st_newhotel_event where event_user_id in ");
		sql.append((new StringBuilder(
				" (select user_id from st_user where agent_id=")).append(
				agentID).append(")").toString());
		System.out.println((new StringBuilder("sql=")).append(sql).toString());
		sdb.setQuerySQL(sql.toString());
		sdb.executeQuery();
		return sdb.getRowCount() >= 1;
	}
}
