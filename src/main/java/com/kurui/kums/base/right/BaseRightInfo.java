package com.kurui.kums.base.right;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.kurui.kums.base.util.StringUtil;

public class BaseRightInfo {
	class Authorization {
		private boolean right;
		private String rightName;
		private ArrayList events;
		final BaseRightInfo this$0;

		public boolean isRight() {
			return right;
		}

		public void setRight(boolean right) {
			this.right = right;
		}

		public void addEvent(String event) {
			events.add(event);
		}

		public boolean hasEvent(String event) {
			for (int i = 0; i < events.size(); i++)
				if (((String) events.get(i)).equals(event))
					return true;

			return false;
		}

		public String getRightName() {
			return rightName;
		}

		public void setRightName(String rightName) {
			this.rightName = rightName;
		}

		Authorization() {
			this$0 = BaseRightInfo.this;
			// super();
			right = false;
			rightName = "";
			events = new ArrayList();
		}
	}

	private static final long serialVersionUID = 1L;
	private boolean right[][];
	private String leftRight[] = { "sa", "sb", "sc", "sd", "se", "sf", "sg",
			"sh", "si", "sj", "sk", "sl", "sm", "sn", "so", "sp", "sq", "sr",
			"ss", "st", "su", "sv", "sw", "sx", "sy", "sz" };
	private ArrayList list;
	private Set rights;

	public boolean hasRight(String rightName, String event) {
		System.out
				.println("BaseRightInfo.hasRight(rightName,event)-------------");
		for (Iterator itr = rights.iterator(); itr.hasNext();) {
			Authorization a = (Authorization) itr.next();
			if (a.getRightName().equals(rightName) && a.hasEvent(event))
				return true;
		}

		return false;
	}

	// public boolean hasRight(String rightName) {
	// boolean flag = false;
	// for (Iterator itr = rights.iterator(); itr.hasNext();) {
	// Authorization a = (Authorization) itr.next();
	// if (a.getRightName().equals(rightName)) {
	// flag = true;
	// }
	// }
	// return flag;
	// }

	public boolean hasRight(String rightName) {
		boolean flag = false;
//		System.out.println("BaseRightInfo.hasRight()-------------" + rightName);
		for (Iterator itr = rights.iterator(); itr.hasNext();) {
			Authorization a = (Authorization) itr.next();
			String authRightName = a.getRightName();
			String[] rightArray = StringUtil.getRightArrayByGroup(rightName);
			for (int i = 0; i < rightArray.length; i++) {
				String tempRightName=rightArray[i];
				if (authRightName.equals(tempRightName)) {
					flag = true;
				}
			}
		}
//		System.out.println("hasRight:" + flag);
		return flag;
	}

	public Authorization getAuthorization(String rightName) {
		for (Iterator itr = rights.iterator(); itr.hasNext();) {
			Authorization a = (Authorization) itr.next();
			if (a.getRightName().equals(rightName))
				return a;
		}

		return null;
	}

	public void addRight(String rightName, String rightAction) {
		try {
			Authorization a = new Authorization();
			a.setRightName(rightName);
			if (rightAction != null) {
				String ras[] = rightAction.split(",");
				for (int i = 0; i < ras.length; i++)
					a.addEvent(ras[i]);

			}
			rights.add(a);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private boolean match(String rightName) {
		String temps[] = new String[0];
		String temp = "";
		for (int i = 0; i < list.size(); i++) {
			temps = ((String) list.get(i)).split(".");
			if (rightName.indexOf(temps[0]) > 0 && temps.length > 1
					&& hasRight(temps[0], temps[1]))
				return true;
		}

		return false;
	}

	public void setRight(String strRightName, boolean bln) {
		try {
			right[getLeftIndex(strRightName)][getRightIndex(strRightName)] = bln;
		} catch (Exception ex) {
			System.out.println((new StringBuilder(String.valueOf(ex
					.getMessage()))).append("--:setRight(")
					.append(strRightName).append(",").append(bln).append(
							") error :right[").append(
							getLeftIndex(strRightName)).append("][").append(
							getRightIndex(strRightName)).append("]=").append(
							bln).toString());
		}
	}

	public void setAuthorization(String strRightName, boolean bln) {
		try {
			right[getLeftIndex(strRightName)][getRightIndex(strRightName)] = bln;
		} catch (Exception ex) {
			System.out.println((new StringBuilder(String.valueOf(ex
					.getMessage()))).append("--:setRight(")
					.append(strRightName).append(",").append(bln).append(
							") error :right[").append(
							getLeftIndex(strRightName)).append("][").append(
							getRightIndex(strRightName)).append("]=").append(
							bln).toString());
		}
	}

	private int getLeftIndex(String strName) {
		for (int i = 0; i < leftRight.length; i++)
			if (leftRight[i].equals(strName.substring(0, 2)))
				return i;

		return -1;
	}

	private int getRightIndex(String strName) {
		return Integer.parseInt(strName.substring(2, 4));
	}

	public BaseRightInfo() {
		right = new boolean[30][30];
		list = new ArrayList();
		rights = new HashSet(0);
		for (int i = 0; i < right[0].length; i++) {
			for (int j = 0; j < right[i].length; j++)
				right[i][j] = false;

		}
	}

	public void setRightAll(boolean b) {
		for (int i = 0; i < right[0].length; i++) {
			for (int j = 0; j < right[i].length; j++)
				right[i][j] = b;

		}
	}

	public void clear() {
		rights.clear();
	}

	public ArrayList getList() {
		return list;
	}

	public void setList(ArrayList list) {
		this.list = list;
	}

	public static void main(String args[]) {
		try {
			BaseRightInfo uri = new BaseRightInfo();
			uri.setRightAll(true);
			System.out.println((new StringBuilder("sd04=")).append(
					uri.hasRight("sn00")).toString());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}