package com.kurui.kums.base.database;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.kurui.kums.base.ListActionForm;

public class BaseDAOSupport extends HibernateDaoSupport {

	private static Log log = LogFactory
			.getLog(com.kurui.kums.base.database.BaseDAOSupport.class);

	public BaseDAOSupport() {
	}

	public void _create(Object entity) {
		try {
			getHibernateTemplate().save(entity);
		} catch (Exception exception) {
		}
	}

	public Query getQuery(String sql) {
		try {
			Query query = getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createQuery(sql);
			return query;

		} catch (Exception e) {
			e.printStackTrace();
			log.error(" query fails:" + e.getMessage());
		}
		return null;
	}

	public Query getQuery(Hql hql) {
		Query query;
		try {
			query = getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createQuery(hql.getSql());
			for (int i = 0; i < hql.getParameters().size(); i++) {
				Object parameter = hql.getParameters().get(i);
				setParameter(query, i, parameter);
			}
			return query;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" query fails:" + e.getMessage());
		}
		return null;
	}

	public Criteria getCriteria(Class clazz) throws Exception {
		return getSessionFactory().getCurrentSession().createCriteria(clazz);
	}

	public int getTotalPage(int totalCount, int pageSize) {
		return ((totalCount + pageSize) - 1) / pageSize;
	}

	public int getTotalCount(String hql) {
		Query query = getQuery((new StringBuilder("select count(*)  ")).append(
				hql.substring(hql.indexOf("from "))).toString());
		if (query == null)
			return 0;
		Object obj = query.uniqueResult();
		if (obj != null) {
			Long l = (Long) obj;
			return l.intValue();
		} else {
			return 0;
		}
	}

	public int getTotalCount(Hql hql) {
		String temp = (new StringBuilder("select count(*)  ")).append(
				hql.getSql().substring(hql.getSql().indexOf("from ")))
				.toString();
		int p = temp.toLowerCase().lastIndexOf(" order");
		if (p > 0)
			temp = temp.substring(0, p);
		Query query = getQuery(temp);
		try {
			for (int i = 0; i < hql.getParameters().size(); i++) {
				Object parameter = hql.getParameters().get(i);
				setParameter(query, i, parameter);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (query == null)
			return 0;
		Object obj = query.uniqueResult();
		if (obj != null) {
			Long l = (Long) obj;
			return l.intValue();
		} else {
			return 0;
		}
	}

	public List list(String hql) {
		Query query = getQuery(hql);
		if (query != null) {
			List list = query.list();
			if (list != null)
				return list;
			else
				return new ArrayList();
		} else {
			return new ArrayList();
		}
	}

	public List list(Hql hql) {
		Query query = getQuery(hql);
		if (query != null) {
			List list = query.list();
			if (list != null)
				return list;
			else
				return new ArrayList();
		} else {
			return new ArrayList();
		}
	}

	public List list(Hql hql, ListActionForm laf) {
		Query query = getQuery(hql);
		int totalRowCount = getTotalCount(hql);
		laf.setTotalRowCount(totalRowCount);
		query.setFirstResult(laf.getStart());
		query.setMaxResults(laf.getPerPageNum());
//		log.info("list(Hql hql,listform) maxResults:"+laf.getPerPageNum());
		if (query != null) {
			List list = query.list();
			if (list != null)
				return list;
			else
				return new ArrayList();
		} else {
			return new ArrayList();
		}
	}

	public List list(String hql, ListActionForm laf) {
		Query query = getQuery(hql);
		int totalRowCount = getTotalCount(hql);
		laf.setTotalRowCount(totalRowCount);
		query.setFirstResult(laf.getStart());
		query.setMaxResults(laf.getPerPageNum());
		log.info("list(string hql,listform) maxResults:"+laf.getPerPageNum());
		if (query != null) {
			List list = query.list();
			if (list != null)
				return list;
			else
				return new ArrayList();
		} else {
			return new ArrayList();
		}
	}

	private Query setParameter(Query query, int i, Object parameter) {
		try {
			if (parameter.getClass().getSimpleName().equalsIgnoreCase("Long")) {
				Method m = query.getClass().getMethod(
						(new StringBuilder("set")).append(
								parameter.getClass().getSimpleName())
								.toString(),
						new Class[] { Integer.TYPE, Long.TYPE });
				m.invoke(query, new Object[] { Integer.valueOf(i), parameter });
			} else if (parameter.getClass().getSimpleName().equalsIgnoreCase(
					"int")
					|| parameter.getClass().getSimpleName().equalsIgnoreCase(
							"integer")) {
				Method m = query.getClass().getMethod(
						(new StringBuilder("set")).append(
								parameter.getClass().getSimpleName())
								.toString(),
						new Class[] { Integer.TYPE, Integer.TYPE });
				m.invoke(query, new Object[] { Integer.valueOf(i), parameter });
			} else {
				Method m = query.getClass().getMethod(
						(new StringBuilder("set")).append(
								parameter.getClass().getSimpleName())
								.toString(),
						new Class[] { Integer.TYPE, parameter.getClass() });
				m.invoke(query, new Object[] { Integer.valueOf(i), parameter });
			}
		} catch (Exception exception) {
		}
		return query;
	}

}
