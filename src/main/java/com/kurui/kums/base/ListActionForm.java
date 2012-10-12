package com.kurui.kums.base;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ListActionForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ==============分页
	int perPageNum;
	int pageCount;
	int firstPage;
	int prevPage;
	int intPage;
	int nextPage;
	int lastPage;
	int listSize;
	int totalRowCount;
	int start;
	BigDecimal totalValue1;
	BigDecimal totalValue2;
	BigDecimal totalValue3;
	BigDecimal totalValue4;
	BigDecimal totalValue5;
	BigDecimal totalValue6;
	BigDecimal totalValue7;
	BigDecimal totalValue8;

	private String lastAction;
	protected String thisAction;
	protected int selectedItems[];
	protected List list;
	private ArrayList totalValues;

	// ================搜索条件
	protected String year;
	protected String month;
	protected String startDate = "";// 开始时间
	protected String endDate = "";// 结束时间
	protected String orderBy = "";

	// ========未知用途
	String scope;

	public ListActionForm() {
		perPageNum = 10;
		pageCount = 0;
		firstPage = 0;
		prevPage = 0;
		intPage = 0;
		nextPage = 0;
		lastPage = 0;
		listSize = 0;
		start = 0;
		resetTotalValue();
		totalRowCount = 0;
		totalValues = new ArrayList();

		lastAction = "";
		thisAction = "";
		selectedItems = new int[0];

		year = "";
		month = "";
		scope = "0";
	}

	public void addSumField(int index, String field) {
		try {
			BigDecimal temp = new BigDecimal(0);
			field = (new StringBuilder(String.valueOf(field.substring(0, 1)
					.toUpperCase()))).append(field.substring(1)).toString();

//			System.out.println("field:" + field);

			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				String getMethodParam = new StringBuilder("get").append(field)
						.toString();
//				System.out.println("getMethodParam:" + getMethodParam);
				Method m = obj.getClass().getMethod(getMethodParam,
						new Class[0]);
				if (m != null)
					temp = temp.add((BigDecimal) m.invoke(obj, new Object[0]));
			}

			String tempField = (new StringBuilder("totalValue")).append(index)
					.toString();

			String setTotalMethodParam = new StringBuilder("setTotalValue")
					.append(index).toString();
//			System.out.println("setTotalMethodParam:" + setTotalMethodParam);
			Method m = getClass().getMethod(setTotalMethodParam,
					new Class[] { java.math.BigDecimal.class });
			
			m.invoke(this, new Object[] { temp.setScale(2, 4) });
			totalValues.add(temp.setScale(2, 4) );
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setTotalRowCount(int totalRowCount) {
		this.totalRowCount = totalRowCount;
		if (totalRowCount < 1) {
			pageCount = 0;
			firstPage = 0;
			prevPage = 0;
			intPage = 0;
			nextPage = 0;
			lastPage = 0;
		} else if (totalRowCount <= perPageNum) {
			pageCount = 1;
			firstPage = 1;
			prevPage = 1;
			intPage = 1;
			nextPage = 1;
			lastPage = 1;
		} else {
			int f = totalRowCount / perPageNum;
			int p = totalRowCount % perPageNum;
			if (p == 0)
				pageCount = f;
			else
				pageCount = f + 1;
			if (intPage == 0)
				intPage = 1;
		}
	}

	// ============reset

	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		perPageNum = 10;
		pageCount = 0;
		firstPage = 0;
		prevPage = 0;
		intPage = 0;
		nextPage = 0;
		lastPage = 0;

		thisAction = "";
		selectedItems = new int[0];
		resetTotalValue();
		totalRowCount = 0;
		totalValues.clear();

		scope = "0";
	}

	public void resetTotalValue() {
		totalValue1 = new BigDecimal(0);
		totalValue2 = new BigDecimal(0);
		totalValue3 = new BigDecimal(0);
		totalValue4 = new BigDecimal(0);
		totalValue5 = new BigDecimal(0);
		totalValue6 = new BigDecimal(0);
		totalValue7 = new BigDecimal(0);
		totalValue8 = new BigDecimal(0);
	}

	public int getTotalRowCount() {
		return totalRowCount;
	}

	public int getFirstPage() {
		return 1;
	}

	public int getIntPage() {
		if (intPage > pageCount)
			return 1;
		else
			return intPage;
	}

	public int getLastPage() {
		return pageCount;
	}

	public int getNextPage() {
		return nextPage;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public int getListSize() {
		return list.size();
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getThisAction() {
		return thisAction;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public void setIntPage(int intPage) {
		this.intPage = intPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}

	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}

	public void setThisAction(String thisAction) {
		if (this.thisAction.length() > 4
				&& this.thisAction.substring(0, 4).equalsIgnoreCase("list"))
			lastAction = thisAction;
		this.thisAction = thisAction;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int[] getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(int selectedItems[]) {
		this.selectedItems = selectedItems;
	}

	public int getPageCount() {
		return pageCount;
	}

	public String getLastAction() {
		return lastAction;
	}

	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}

	public BigDecimal getTotalValue1() {
		return totalValue1;
	}

	public void setTotalValue1(BigDecimal totalValue1) {
		this.totalValue1 = totalValue1;
	}

	public BigDecimal getTotalValue2() {
		return totalValue2;
	}

	public void setTotalValue2(BigDecimal totalValue2) {
		this.totalValue2 = totalValue2;
	}

	public BigDecimal getTotalValue3() {
		return totalValue3;
	}

	public void setTotalValue3(BigDecimal totalValue3) {
		this.totalValue3 = totalValue3;
	}

	public BigDecimal getTotalValue4() {
		return totalValue4;
	}

	public void setTotalValue4(BigDecimal totalValue4) {
		this.totalValue4 = totalValue4;
	}

	public BigDecimal getTotalValue5() {
		return totalValue5;
	}

	public void setTotalValue5(BigDecimal totalValue5) {
		this.totalValue5 = totalValue5;
	}

	public BigDecimal getTotalValue6() {
		return totalValue6;
	}

	public void setTotalValue6(BigDecimal totalValue6) {
		this.totalValue6 = totalValue6;
	}

	public BigDecimal getTotalValue7() {
		return totalValue7;
	}

	public void setTotalValue7(BigDecimal totalValue7) {
		this.totalValue7 = totalValue7;
	}

	public BigDecimal getTotalValue8() {
		return totalValue8;
	}

	public void setTotalValue8(BigDecimal totalValue8) {
		this.totalValue8 = totalValue8;
	}
	
	

	public ArrayList getTotalValues() {
		return totalValues;
	}

	public void setTotalValues(ArrayList totalValues) {
		this.totalValues = totalValues;
	}

	public int getStart() {
		if (getIntPage() > pageCount)
			start = 0;
		else if (getIntPage() <= 1)
			start = 0;
		else
			start = (getIntPage() - 1) * perPageNum;
		return start;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public static void main(String args[]) {
		ListActionForm laf = new ListActionForm();
		laf.setPerPageNum(8);
		laf.setTotalRowCount(13);
	}
}
