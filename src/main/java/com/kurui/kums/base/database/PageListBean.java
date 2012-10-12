package com.kurui.kums.base.database;

import com.kurui.kums.base.util.StringUtil;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;

public class PageListBean {

	private int nextPage;
	private int perPage;
	private String querySQL;
	private ArrayList fieldVector;
	private ArrayList valueVector;
	private Connection dbConn;
	private boolean ifNext;
	private boolean isProcedure;
	private int rowNum;
	private int totalRowCount;
	private int pageCount;
	private String quickField;
	private String quickValue;
	private int quickRow;
	private int curPage;
	private int colCount;
	private CallableStatement cstmt;
	private Statement stmt;

	public PageListBean() {
		nextPage = 0;
		perPage = 15;
		fieldVector = new ArrayList();
		valueVector = new ArrayList();
		ifNext = false;
		isProcedure = false;
		rowNum = 0;
		totalRowCount = 0;
		quickField = "";
		quickValue = "";
		colCount = 0;
		cstmt = null;
		stmt = null;
		init();
	}

	private void init() {
		try {
			for (int count = 0; stmt == null && count <= 3; count++) {
				if (count > 0)
					wait(500L);
				dbConn = DBCHPool.getConnection();
				stmt = dbConn.createStatement(1004, 1007);
			}

			if (isProcedure)
				cstmt = dbConn.prepareCall(querySQL, 1004, 1007);
		} catch (Exception exception) {
		}
	}

	private void init2() {
		try {
			for (int count = 0; stmt == null && count++ <= 5; count++) {
				wait(100L);
				dbConn = DBCHPool.getConnection();
				stmt = dbConn.createStatement(1004, 1007);
			}

			if (isProcedure)
				cstmt = dbConn.prepareCall(querySQL, 1004, 1007);
		} catch (Exception exception) {
		}
	}

	private boolean executeQuery() {
		boolean returnVaue = true;
		int beginRow = 0;
		boolean moveIF = true;
		String lineOne = "";
		ResultSet rst = null;
		ResultSetMetaData rstMetaData = null;
		int columnCount = 0;
		String temp = "";
		rowNum = 0;
		if (querySQL == "") {
			writeLog("??????????");
			quickField = "";
			quickValue = "";
			quickRow = -1;
			return false;
		}
		quickRow = -1;
		try {
			if (isProcedure) {
				rst = cstmt.executeQuery();
			} else {
				if (stmt == null)
					init2();
				rst = stmt.executeQuery(querySQL);
			}
		} catch (Exception e) {
			writeLog((new StringBuilder("??��???????")).append(e.toString())
					.append(",sql=").append(querySQL).toString());
			try {
				if (isProcedure && cstmt != null)
					cstmt.close();
				else if (stmt != null)
					stmt.close();
			} catch (SQLException sqlexception) {
			}
			returnVaue = false;
			return returnVaue;
		}
		try {
			rst.last();
			totalRowCount = rst.getRow();
			rst.beforeFirst();
		} catch (Exception ex) {
			totalRowCount = 0;
			writeLog((new StringBuilder("??��???????")).append(ex.toString())
					.toString());
			return returnVaue;
		}
		pageCount = BigInteger.valueOf(totalRowCount).divide(
				BigInteger.valueOf(perPage)).intValue();
		if (BigInteger.valueOf(totalRowCount).mod(BigInteger.valueOf(perPage))
				.intValue() > 0)
			pageCount++;
		if (pageCount == 0)
			nextPage = 0;
		else if (nextPage > pageCount)
			nextPage = 0;
		beginRow = nextPage * perPage;
		beginRow++;
		curPage = nextPage;
		if (pageCount <= 1) {
			ifNext = false;
			nextPage = 0;
		} else if (nextPage > pageCount) {
			ifNext = false;
			nextPage = 0;
		} else {
			ifNext = true;
		}
		if (nextPage < 0)
			nextPage = 0;
		if (quickField != null && quickField.compareTo("") != 0
				&& pageCount != 0 && quickValue != null
				&& quickValue.compareTo("") != 0)
			try {
				int colIndex = 0;
				String quickFieldName = "";
				String quickFieldType = "";
				String quickFieldTmp[] = (String[]) null;
				boolean haveFound = false;
				int findRow = 0;
				quickFieldTmp = StringUtil.split(quickField, ",");
				if (quickFieldTmp == null)
					throw new Exception((new StringBuilder("??????��??"))
							.append(quickField).toString());
				quickFieldName = quickFieldTmp[0];
				quickFieldType = quickFieldTmp[1];
				colIndex = rst.findColumn(quickFieldName);
				rst.beforeFirst();
				while (!haveFound && rst.next()) {
					String tmpValue = "";
					if (rst.getObject(colIndex) != null) {
						tmpValue = rst.getObject(colIndex).toString();
						if (quickFieldType.compareToIgnoreCase("date") == 0) {
							int indexPos = -1;
							tmpValue = StringUtil.mk_date(tmpValue, 2);
							indexPos = tmpValue.indexOf(quickValue);
							if (indexPos == -1) {
								haveFound = false;
							} else {
								haveFound = true;
								findRow = rst.getRow();
							}
						} else if (quickFieldType.compareToIgnoreCase("long") == 0) {
							int indexPos = -1;
							indexPos = tmpValue.compareTo(quickValue);
							if (indexPos == 0) {
								haveFound = true;
								findRow = rst.getRow();
							} else {
								haveFound = false;
							}
						} else if (quickFieldType.compareToIgnoreCase("char") == 0) {
							int indexPos = -1;
							indexPos = tmpValue.indexOf(quickValue);
							if (indexPos == -1) {
								haveFound = false;
							} else {
								haveFound = true;
								findRow = rst.getRow();
							}
						}
					}
				}
				if (findRow > 0) {
					int pageTmp = 0;
					int modTmp = 0;
					pageTmp = BigInteger.valueOf(findRow).divide(
							BigInteger.valueOf(perPage)).intValue();
					modTmp = BigInteger.valueOf(findRow).mod(
							BigInteger.valueOf(perPage)).intValue();
					if (modTmp == 0) {
						nextPage = pageTmp - 1;
						quickRow = perPage;
					} else {
						nextPage = pageTmp;
						quickRow = modTmp;
					}
					beginRow = nextPage * perPage;
					beginRow++;
					curPage = nextPage;
					if (nextPage >= pageCount - 1) {
						ifNext = false;
						nextPage = 0;
					} else {
						ifNext = true;
						nextPage = nextPage + 1;
					}
				}
			} catch (Exception exx) {
				writeLog(exx.toString());
				quickRow = -1;
			}
		quickField = "";
		quickValue = "";
		try {
			rstMetaData = rst.getMetaData();
		} catch (Exception e) {
			writeLog((new StringBuilder("???????")).append(e.toString())
					.toString());
			returnVaue = false;
		}
		try {
			columnCount = rstMetaData.getColumnCount();
		} catch (Exception e) {
			writeLog((new StringBuilder("??��??")).append(e.toString())
					.toString());
			returnVaue = false;
		}
		Object object = null;
		for (int i = 1; i <= columnCount; i++)
			try {
				fieldVector.add(rstMetaData.getColumnName(i));
			} catch (Exception e) {
				writeLog((new StringBuilder("Exception while getColumnName?G"))
						.append(e.toString()).toString());
			}

		try {
			if (pageCount > 0) {
				try {
					rst.absolute(beginRow);
					moveIF = true;
				} catch (Exception exx) {
					moveIF = false;
				}
				if (moveIF) {
					for (int i = 1; i <= columnCount; i++) {
						try {
							object = rst.getObject(i);
							if (object == null)
								temp = "";
							else
								temp = object.toString();
						} catch (Exception e) {
							temp = "";
						}
						if (temp == null)
							temp = "";
						lineOne = (new StringBuilder(String.valueOf(lineOne)))
								.append(temp).toString();
						valueVector.add(temp);
					}

					if (lineOne.equals("")) {
						valueVector = new ArrayList();
						rowNum = 0;
					} else {
						rowNum = 1;
					}
					for (int i = 1; i < perPage; i++)
						if (rst.next()) {
							rowNum++;
							for (int k = 1; k <= columnCount; k++) {
								try {
									if (rst.getObject(k) == null)
										temp = "";
									else
										temp = rst.getObject(k).toString();
								} catch (Exception e) {
									temp = "";
								}
								if (temp == null)
									temp = "";
								valueVector.add(temp);
							}

						}

				}
			}
		} catch (Exception e) {
			writeLog((new StringBuilder("??????")).append(e.toString())
					.toString());
			returnVaue = false;
		}
		try {
			rst.close();
		} catch (Exception exception) {
		}
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception exception1) {
		}
		if (!returnVaue)
			resetBean();
		colCount = columnCount;
		close();
		return returnVaue;
	}

	public int getColCount() {
		return colCount;
	}

	public String getColValue(int vRowNo, int vColNo) {
		int columnNo = 0;
		columnNo = fieldVector.size();
		if (vColNo <= 0 || vColNo > columnNo)
			return "";
		if (vRowNo <= 0 || vRowNo > getRowCount())
			return "";
		else
			return valueVector.get(columnNo * (vRowNo - 1) + (vColNo - 1))
					.toString();
	}

	public String getColValue(int vRowNo, String vFieldName) {
		int columnNo = 0;
		columnNo = fieldVector.size();
		for (int i = 0; i < columnNo; i++)
			if (fieldVector.get(i).toString().toUpperCase().compareTo(
					vFieldName.toUpperCase()) == 0)
				return valueVector.get(columnNo * (vRowNo - 1) + i).toString();

		return "";
	}

	public int getCurPage() {
		return curPage;
	}

	public boolean getIfNext() {
		return ifNext;
	}

	public boolean setIsProcedure(boolean isPro) {
		return isProcedure = isPro;
	}

	public int getNextPage() {
		return nextPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getPerPage() {
		return perPage;
	}

	public String getQuerySQL() {
		return querySQL;
	}

	public int getQuickRow() {
		return quickRow;
	}

	public int getTotalRowCount() {
		return totalRowCount;
	}

	public int getRowCount() {
		return rowNum;
	}

	private void resetBean() {
		fieldVector = null;
		fieldVector = new ArrayList();
		valueVector = null;
		valueVector = new ArrayList();
		ifNext = false;
		isProcedure = false;
		curPage = 0;
		rowNum = 0;
		pageCount = 0;
		colCount = 0;
	}

	private void setColCount(int newColCount) {
		colCount = newColCount;
	}

	public void setNextPage(int nNextPage) {
		nextPage = nNextPage;
	}

	public void setPerPage(int nPerPage) {
		perPage = nPerPage;
	}

	public boolean setQuerySQL(String uSQL) {
		resetBean();
		querySQL = uSQL;
		return executeQuery();
	}

	public void setQuickField(String newQuickField) {
		quickField = newQuickField;
	}

	void setQuickRow(int newQuickRow) {
		quickRow = newQuickRow;
	}

	public void setQuickValue(String newQuickValue) {
		quickValue = newQuickValue;
	}

	private void writeLog(String param) {
		try {
			System.out.println((new StringBuilder()).append(
					new Timestamp(System.currentTimeMillis())).append("  ")
					.append("[com.database.DevidePageBean]").append(" ")
					.append(param).toString());
		} catch (Exception exception) {
		}
	}

	public String[] getFieldList() {
		String retValue[] = (String[]) null;
		if (fieldVector == null)
			return null;
		fieldVector.trimToSize();
		if (fieldVector.size() <= 0) {
			return null;
		} else {
			retValue = (String[]) fieldVector.toArray(new String[0]);
			return retValue;
		}
	}

	public void printlnValue() {
		for (int j = 0; j < valueVector.size(); j++)
			System.out.println((new StringBuilder("valueVector[")).append(j)
					.append("]=").append((String) valueVector.get(j))
					.toString());

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
				System.out.println((new StringBuilder(
						"close sdb connection false:")).append(ex.getMessage())
						.toString());
			}
	}

	public static void main(String arg[]) {
		try {
			PageListBean plb = new PageListBean();
			int intPage = 1;
			int perPageNum = 10;
			plb.setPerPage(11);
			plb.setNextPage(intPage - 1);
			int rowcount = 0;
			int pagecount = 0;
			plb
					.setQuerySQL("select * from st_agent a  where a.agent_status in (1,0)  and a.agent_type=1 and (agent_cname like '%???��%' or a.agent_code like '%???��%' or a.apply_name like '%???��%' or a.apply_subtel like '%???��%') order by update_date desc,agent_cname,agent_rank");
			rowcount = plb.getRowCount();
			pagecount = plb.getPageCount();
			for (int i = 1; i < rowcount + 1; i++)
				System.out.println((new StringBuilder("apply_name=")).append(
						plb.getColValue(i, "apply_name")).toString());

		} catch (Exception exception) {
		}
	}
}
