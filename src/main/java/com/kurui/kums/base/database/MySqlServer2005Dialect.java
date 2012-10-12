package com.kurui.kums.base.database;

import org.hibernate.dialect.SQLServerDialect;

public class MySqlServer2005Dialect extends SQLServerDialect {

	public MySqlServer2005Dialect() {
	}

	static int getLastIndexOfOrderBy(String sql) {
		return sql.toLowerCase().lastIndexOf("order by ");
	}

	public String getLimitString(String querySelect, int offset, int limit) {
		int lastIndexOfOrderBy = getLastIndexOfOrderBy(querySelect);
		if (lastIndexOfOrderBy < 0 || querySelect.endsWith(")") || offset == 0) {
			return super.getLimitString(querySelect, 0, limit);
		} else {
			String orderby = querySelect.substring(lastIndexOfOrderBy,
					querySelect.length());
			int indexOfFrom = querySelect.toLowerCase().indexOf("from");
			String selectFld = querySelect.substring(0, indexOfFrom);
			String selectFromTableAndWhere = querySelect.substring(indexOfFrom,
					lastIndexOfOrderBy);
			StringBuffer sql = new StringBuffer(querySelect.length() + 100);
			sql.append("select * from (").append(selectFld).append(
					",ROW_NUMBER() OVER(").append(orderby).append(
					") as _page_row_num_hb ").append(selectFromTableAndWhere)
					.append(" ) temp ").append(
							" where  _page_row_num_hb BETWEEN  ").append(
							offset + 1).append(" and ").append(limit);
			return sql.toString();
		}
	}

	public boolean supportsLimitOffset() {
		return true;
	}
}
