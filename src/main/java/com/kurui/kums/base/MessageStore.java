package com.kurui.kums.base;

import java.util.HashMap;
import java.util.Map;

public class MessageStore {
	private String code = "";
	private String content = "";
	protected static Map<String, String> messageMap = new HashMap<String, String>();

	static {
		messageMap.put("ERROR", "程序异常,请联系技术支持");

		messageMap.put("NULLFORM", "表单为空");

		messageMap.put("NOORDER", "订单不存在");// NOORDER
		messageMap.put("OVERLOOKED", "已经锁定，请勿重复修改");

		messageMap.put("ACCOUNTERROR", "账号设置异常,请联系管理员");
		messageMap.put("STATEMENTACCOUNTERROR", "STATEMENT账号设置异常,请联系管理员");

	}

	public static String getContent(String code) {
		boolean contain = messageMap.containsKey(code);

		if (contain) {
			return messageMap.get(code);
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
