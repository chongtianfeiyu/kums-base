package com.kurui.kums.base.util;

import com.kurui.kums.base.database.Hql;
import com.kurui.kums.base.database.SelectDataBean;

public class NoDBUtil extends NoUtil{

	public String getNewNo(String c, String pattern, int length) {

		String temp = c + DateUtil.getDateString(pattern);
		int subIndex = c.length() + 8;
		try {
			Hql hql = new Hql("select next_no from no where type='" + c + "'");
			hql.add(" and  substr(next_no,0," + subIndex + ")='" + temp + "'");
			SelectDataBean sdb = new SelectDataBean();

			sdb.setQuerySQL(hql.getSql());
			sdb.executeQuery();
			String oldNoticeNo = "";
			String newNumber = "";

			if (sdb == null) {
				return temp + "FF" + NoUtil.getRandom(5);
			} else if (sdb.getRowCount() < 1) {
				newNumber = temp + "000001";
				hql.clear();
				hql
						.add("insert into no(id,next_no,type) values(seq_no.nextval,'"
								+ temp + "000001" + "','" + c + "')");
				sdb.executeUpdateSQL(hql.getSql());
			} else {
				int num = 0;
				oldNoticeNo = sdb.getColValue(1, "next_no");
				if (oldNoticeNo.length() >= subIndex) {
					oldNoticeNo = oldNoticeNo.substring(subIndex);
					num = com.kurui.kums.base.Constant.toInt(oldNoticeNo);
					if (num == 0)
						oldNoticeNo = "1";
					else
						oldNoticeNo = String.valueOf(++num);
				} else
					oldNoticeNo = "1";
				newNumber = temp + transValue(oldNoticeNo, length);
				hql.clear();
				hql.add("update no set next_no='" + newNumber
						+ "' where type='" + c + "'");

				sdb.executeUpdateSQL(hql.getSql());
			}

			sdb.close();

			return newNumber;
		} catch (Exception ex) {
			return temp + "0" + NoUtil.getRandom(5);
		}
	}
}
