package com.kurui.kums.base.database;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class HibernateLazyResolver implements InitializingBean {

	private static Log logger = LogFactory
			.getLog(com.kurui.kums.base.database.HibernateLazyResolver.class);
	private boolean singleSession;
	private SessionFactory sessionFactory;
	boolean participate;
	protected Session session;

	public HibernateLazyResolver() {
		singleSession = true;
		participate = false;
		session = null;
	}

	public final void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setSingleSession(boolean singleSession) {
		this.singleSession = singleSession;
	}

	protected boolean isSingleSession() {
		return singleSession;
	}

	public void afterPropertiesSet() throws Exception {
		if (sessionFactory == null)
			throw new IllegalArgumentException("SessionFactory is reqirued!");
		else
			return;
	}

	public void openSession() {
		if (isSingleSession()) {
			if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
				participate = true;
			} else {
				logger
						.debug("Opening single Hibernate Session in HibernateLazyResolver");
				session = getSession(sessionFactory);
				TransactionSynchronizationManager.bindResource(sessionFactory,
						new SessionHolder(session));
			}
		} else if (SessionFactoryUtils.isDeferredCloseActive(sessionFactory))
			participate = true;
		else
			SessionFactoryUtils.initDeferredClose(sessionFactory);
	}

	public void releaseSession() {
		if (!participate)
			if (isSingleSession()) {
				TransactionSynchronizationManager
						.unbindResource(sessionFactory);
				logger
						.debug("Closing single Hibernate Session in HibernateLazyResolver");
				try {
					closeSession(session, sessionFactory);
				} catch (RuntimeException ex) {
					logger
							.error(
									"Unexpected exception on closing Hibernate Session",
									ex);
				}
			} else {
				SessionFactoryUtils.processDeferredClose(sessionFactory);
			}
	}

	protected Session getSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		session.setFlushMode(FlushMode.AUTO);
		return session;
	}

	protected void closeSession(Session session, SessionFactory sessionFactory) {
		session.flush();
		SessionFactoryUtils.releaseSession(session, sessionFactory);
	}

}
