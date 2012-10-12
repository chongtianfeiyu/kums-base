package com.kurui.kums.base;

import org.apache.log4j.Logger;

/**
 * 运行性能监听
 * 
 * @author YanRui
 */
public class PerformListener implements Runnable {
	private String actionName = "";
	private long beginTime;

	static Logger logger = Logger.getLogger(PerformListener.class.getName());

	/**
	 * 监听器(默认)
	 */
	public PerformListener(String actionName, long beginTime) {
		super();
		this.actionName = actionName;
		this.beginTime = beginTime;
		run();
	}

	public void run() {
		try {
			long endTime = System.currentTimeMillis();
			logger.info("========Monitor Flag====" + actionName + "==time:"
					+ ((endTime - beginTime) / 1000) + "s");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("监听异常：" + e.getMessage());
		}
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

}