package com.kurui.kums.base;

import com.kurui.kums.base.database.SelectDataBean;

public class Event implements Cloneable {

	private String relationID;
	private int eventUserID;
	private String eventUserName;
	private String eventType;
	private String eventNote;
	private String eventDate;
	private int intIndex;

	public Event() {
		relationID = "";
		eventUserID = 0;
		eventUserName = "";
		eventType = "";
		eventNote = "";
		eventDate = "";
		intIndex = 0;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventNote() {
		return eventNote.replaceAll("[\\r\\n]+", "<br>");
	}

	public void setEventNote(String eventNote) {
		this.eventNote = eventNote;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public int getEventUserID() {
		return eventUserID;
	}

	public void setEventUserID(int eventUserID) {
		this.eventUserID = eventUserID;
	}

	public String getEventUserName() {
		return eventUserName;
	}

	public void setEventUserName(String eventUserName) {
		this.eventUserName = eventUserName;
	}

	public int getIntIndex() {
		return intIndex;
	}

	public void setIntIndex(int intIndex) {
		this.intIndex = intIndex;
	}

	public String getRelationID() {
		return relationID;
	}

	public void setRelationID(String relationID) {
		this.relationID = relationID;
	}

	public String getUserIDOfEvent(String relationTable, String reserveID,
			String eventType) {
		String sql = "";
		if (relationTable.equals("st_book_event"))
			sql = (new StringBuilder("select top 1 event_user_id from "))
					.append(relationTable).append(" where book_id=").append(
							reserveID).append(" and event_type=").append(
							eventType).append(" order by event_date desc")
					.toString();
		else
			sql = (new StringBuilder("select top 1 event_user_id from "))
					.append(relationTable).append(" where reserve_id=").append(
							reserveID).append(" and event_type=").append(
							eventType).append(" order by event_date desc")
					.toString();
		SelectDataBean sdb = new SelectDataBean();
		sdb.setQuerySQL(sql);
		sdb.executeQuery();
		int rowcount = sdb.getRowCount();
		if (rowcount == 1)
			return sdb.getColValue(1, 1);
		else
			return "";
	}
}
